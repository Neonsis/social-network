import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import SockJS from "sockjs-client";
import Stomp, {Message} from "stompjs";
import {IUser} from "../models/user";
import agent from "../api/agent";
import {IMessage} from "../models/chat";
import {v1 as uuidv1} from 'uuid';
import {store} from 'react-notifications-component';

const MESSAGE_PAGE_SIZE = 15;

export default class ChatStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable stompClient: Stomp.Client | null = null;
    @observable chats: IUser[] = [];
    @observable activeUserChat: IUser | null = null;
    @observable messages: IMessage[] = [];
    @observable loadingChats = true;
    @observable loadingMessages = true;
    @observable isLastMessage: boolean = false;
    @observable messagePage: number = 0;

    @action connectMessages = async () => {
        const token = this.rootStore.commonStore.token;
        const sockJS = new SockJS("http://localhost:8081/api/ws");
        this.stompClient = Stomp.over(sockJS);
        this.stompClient.connect({"Authorization": `Bearer ${token}`}, this.onConnected);
    }

    @action loadActiveUser = async (recipientId: string) => {
        try {
            const activeUser = await agent.User.get(recipientId);
            runInAction(() => {
                this.activeUserChat = activeUser;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadChats = async () => {
        this.loadingChats = true;
        try {
            const chats = await agent.Chat.getChats();
            runInAction(() => {
                this.chats = chats.content;
                this.loadingChats = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingChats = false;
            })
        }
    }

    @action loadMessages = async (recipientId: string) => {
        this.messagePage = 0;
        this.loadingMessages = true;
        try {
            const messages = await agent.Chat.getMessages(recipientId, MESSAGE_PAGE_SIZE, this.messagePage);
            runInAction(() => {
                this.messages = messages.content;
                this.loadingMessages = false;
                this.isLastMessage = messages.last;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingMessages = false;
            })
        }
    }

    @action fetchMoreMessages = async (recipientId: string) => {
        try {
            const messages = await agent.Chat.getMessages(recipientId, MESSAGE_PAGE_SIZE, this.messagePage + 1);
            runInAction(() => {
                this.messages = [...this.messages, ...messages.content];
                this.isLastMessage = messages.last;
                this.messagePage = messages.number;
            })
        } catch (error) {
            console.log(error);
        }
    }

    onConnected = () => {
        const userId = this.rootStore.userStore.user?.id;
        this.stompClient!.subscribe(
            `/user/${userId}/queue/messages`,
            this.onMessageReceived
        );
    };

    onMessageReceived = (message: Message) => {
        const newMessage: IMessage = JSON.parse(message.body);
        runInAction(() => {
            if (newMessage.sender.id === this.activeUserChat?.id) {
                this.messages = [newMessage, ...this.messages];
            }
            store.addNotification({
                message: `Новое сообщение от ${newMessage.sender.lastName} ${newMessage.sender.firstName}`,
                type: "success",
                insert: "top",
                container: "bottom-left",
                animationIn: ["animate__animated", "animate__fadeIn"],
                animationOut: ["animate__animated", "animate__fadeOut"],
                dismiss: {
                    duration: 5000,
                    onScreen: true
                }
            })
        })
    };

    @action sendMessage = async (content: string, recipientId: string) => {
        const message = {recipientId, content}
        this.stompClient!.send("/app/chat", {}, JSON.stringify(message));
        const newMessage: IMessage = {
            id: uuidv1().toString(),
            createdAt: new Date().toISOString(),
            content,
            sender: this.rootStore.userStore.user!
        }
        this.messages = [newMessage, ...this.messages];
    }
}