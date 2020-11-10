import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";

export default class FriendshipStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable loading = false;

    @action addToFriends = async (friendId: string) => {
        this.loading = true;
        try {
            await agent.Friendship.post(friendId);
            runInAction(() => {
                this.loading = false;
                this.rootStore.profileStore.userProfile!.isPendingFriendship = true;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loading = false
            })
        }
    }

    @action deleteFriendship = async (friendId: string) => {
        this.loading = true;
        try {
            await agent.Friendship.delete(friendId);
            runInAction(() => {
                this.loading = false;
                this.rootStore.profileStore.userProfile!.isFriend = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loading = false
            })
        }
    }

    @action confirmFriendship = async (friendId: string) => {
        this.loading = true;
        try {
            await agent.Friendship.post(friendId);
            runInAction(() => {
                this.loading = false;
                this.rootStore.profileStore.userProfile!.isPendingFriendship = false;
                this.rootStore.profileStore.userProfile!.isFollower = false;
                this.rootStore.profileStore.userProfile!.isFriend = true;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loading = false
            })
        }
    }

    @action cancelFriendship = async (friendId: string) => {
        this.loading = true;
        try {
            await agent.Friendship.delete(friendId);
            runInAction(() => {
                this.loading = false;
                this.rootStore.profileStore.userProfile!.isPendingFriendship = false;
            });
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loading = false
            })
        }
    }
}