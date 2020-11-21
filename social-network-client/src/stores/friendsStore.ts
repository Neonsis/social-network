import {RootStore} from "./rootStore";
import {IUser} from "../models/user";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";

const FRIENDS_PAGE_SIZE = 10;

export default class FriendsStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable friends: IUser[] = [];
    @observable followers: IUser[] = [];
    @observable loadingFriends = true;
    @observable page = 0;
    @observable isLastFriends = false;
    @observable isLastFollowers = false;
    @observable loadingFriendsRequest = false;

    @action loadFriends = async (userId: string, search: string) => {
        this.loadingFriends = true;
        try {
            const friends = await agent.Friendship.getFriends(userId, search, this.page, FRIENDS_PAGE_SIZE);
            runInAction(() => {
                this.friends = friends.content;
                this.isLastFriends = friends.last;
                this.loadingFriends = false;
            })
        } catch (e) {
            console.log(e)
            this.loadingFriends = false;
        }
    }

    @action fetchMoreFriends = async (userId: string, search: string) => {
        this.loadingFriends = true;
        try {
            const friends = await agent.Friendship.getFriends(userId, search, this.page, FRIENDS_PAGE_SIZE);
            runInAction(() => {
                this.friends = [...this.friends, ...friends.content];
                this.isLastFriends = friends.last;
                this.page = this.page = 1;
                this.loadingFriends = false;
            })
        } catch (e) {
            console.log(e)
            this.loadingFriends = false;
        }
    }

    @action loadFollowers = async (userId: string) => {
        this.loadingFriends = true;
        try {
            const followers = await agent.Friendship.getFollowers(userId, this.page, FRIENDS_PAGE_SIZE);
            runInAction(() => {
                this.followers = followers.content;
                this.isLastFollowers = followers.last;
                this.loadingFriends = false;
            })
        } catch (e) {
            console.log(e)
            this.loadingFriends = false;
        }
    }

    @action fetchMoreFollowers = async (userId: string) => {
        this.loadingFriends = true;
        try {
            const followers = await agent.Friendship.getFollowers(userId, this.page, FRIENDS_PAGE_SIZE);
            runInAction(() => {
                this.followers = [...this.friends, ...followers.content];
                this.isLastFollowers = followers.last;
                this.page = this.page = 1;
                this.loadingFriends = false;
            })
        } catch (e) {
            console.log(e)
            this.loadingFriends = false;
        }
    }

    @action addToFriends = async (userId: string) => {
        this.loadingFriendsRequest = true;
        try {
            await agent.Friendship.post(userId);
            runInAction(() => {
                this.followers = this.followers.filter(user => user.id !== userId);
                this.loadingFriendsRequest = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingFriendsRequest = false
            })
        }
    }
}