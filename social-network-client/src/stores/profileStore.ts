import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";
import {IUserDetails} from "../models/user";
import {IProfileDetails} from "../models/profile";

export default class ProfileStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable loadingPage = true;
    @observable loadingProfileDetails = true;
    @observable user: IUserDetails | null = null;
    @observable profileDetails: IProfileDetails | null = null;

    @action loadUser = async (userId: string) => {
        this.loadingPage = true;
        try {
            const userProfile = await agent.User.get(userId);
            runInAction(() => {
                const loggedInUser = this.rootStore.userStore.user;
                userProfile.isLoggedInUser = loggedInUser!.id === userId;
                this.user = userProfile;
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.loadingPage = false;
            })
        }
    }

    @action loadProfileDetails = async (userId: string) => {
        this.loadingProfileDetails = true;
        try {
            const profileDetails = await agent.User.profileDetails(userId);
            runInAction(() => {
                this.profileDetails = profileDetails;
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.loadingProfileDetails = false;
            })
        }
    }
}