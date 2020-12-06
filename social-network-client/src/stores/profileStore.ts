import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";
import {IUserDetails} from "../models/user";
import {IProfileDetails} from "../models/profile";
import {IGroup} from "../models/groups";
import {Page} from "../models/page";

export default class ProfileStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable loadingPage = true;
    @observable loadingProfileDetails = true;
    @observable successUpdated = false;
    @observable loadingSaveProfileDetails = false;
    @observable user: IUserDetails | null = null;
    @observable profileDetails: IProfileDetails | null = null;
    @observable profileGroups: Page<IGroup[]> | null = null;
    @observable loadingGroups = true;

    @action loadUserGroups = async (userId: string) => {
        this.loadingGroups = true;
        try {
            const userGroups = await agent.Group.getUserGroups(userId, "", 0, 6);
            runInAction(() => {
                this.profileGroups = userGroups;
                this.loadingGroups = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadingGroups = false;
            })
        }
    }

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
        this.successUpdated = false;
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

    @action saveProfileDetails = async (details: IProfileDetails) => {
        this.loadingSaveProfileDetails = true;
        this.successUpdated = false;
        try {
            const profileDetails = await agent.User.saveDetails(details);
            runInAction(() => {
                this.profileDetails = profileDetails;
                this.loadingSaveProfileDetails = false;
                this.successUpdated = true;
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.loadingSaveProfileDetails = false;
            })
        }
    }
}