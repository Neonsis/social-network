import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";
import {IUser} from "../models/user";
import {Page} from "../models/page";

export default class FriendshipStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable loadingFriends = false;
    @observable loadingFriendsRequest = false;
    @observable profileFriends: Page<IUser[]> | null = null;

    @action loadProfileFriends = async (userId: string) => {
        this.loadingFriends = true;
        try {
            const friends = await agent.Friendship.getFriends(userId, 0, 6);
            runInAction(() => {
                this.profileFriends = friends;
                this.loadingFriends = false;
            })
        } catch (error) {
            runInAction(() => {
                this.loadingFriends = false;
            })
            console.log(error);
        }
    }

    @action addToFriends = async (friendId: string) => {
        this.loadingFriendsRequest = true;
        try {
            await agent.Friendship.post(friendId);
            runInAction(() => {
                this.loadingFriendsRequest = false;
                this.rootStore.profileStore.user!.isPendingFriendship = true;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingFriendsRequest = false
            })
        }
    }

    @action deleteFriendship = async (friendId: string) => {
        this.loadingFriendsRequest = true;
        try {
            await agent.Friendship.delete(friendId);
            runInAction(() => {
                this.loadingFriendsRequest = false;
                this.rootStore.profileStore.user!.isFriend = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingFriendsRequest = false
            })
        }
    }

    @action confirmFriendship = async (friendId: string) => {
        this.loadingFriendsRequest = true;
        try {
            await agent.Friendship.post(friendId);
            runInAction(() => {
                this.rootStore.profileStore.user!.isPendingFriendship = false;
                this.rootStore.profileStore.user!.isFollower = false;
                this.rootStore.profileStore.user!.isFriend = true;
                this.loadingFriendsRequest = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingFriendsRequest = false
            })
        }
    }

    @action cancelFriendship = async (friendId: string) => {
        this.loadingFriendsRequest = true;
        try {
            await agent.Friendship.delete(friendId);
            runInAction(() => {
                this.rootStore.profileStore.user!.isPendingFriendship = false;
                this.loadingFriendsRequest = false;
            });
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingFriendsRequest = false
            })
        }
    }
}