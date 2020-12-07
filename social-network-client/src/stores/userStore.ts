import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {IUser, IUserFormValues} from "../models/user";
import agent from "../api/agent";

export default class UserStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable user: IUser | null = null;
    @observable loading = false;

    @action getUser = async () => {
        this.loading = true;
        try {
            const user = await agent.User.current();
            runInAction(() => {
                this.loading = false;
                this.user = user;
            })
        } catch (error) {
            this.rootStore.commonStore.setToken(null);
            runInAction(() => {
                this.loading = false;
            });
        }
    };

    @action login = async (values: IUserFormValues) => {
        this.loading = true;
        try {
            const user = await agent.User.login(values);
            runInAction(() => {
                this.loading = false;
            });
            this.rootStore.commonStore.setToken(user.token);
        } catch (error) {
            runInAction(() => {
                this.loading = false;
            });
            throw error;
        }
    };

    @action register = async (values: IUserFormValues) => {
        this.loading = true;
        try {
            const user = await agent.User.signup(values);
            runInAction(() => {
                this.loading = false;
                this.rootStore.commonStore.setToken(user.token);
            });
        } catch (error) {
            runInAction(() => {
                this.loading = false;
            });
            throw error;
        }
    };

    @action logout = async () => {
        try {
            await agent.User.logout();
            runInAction(() => {
                this.user = null;
            });
            this.rootStore.commonStore.setToken(null);
        } catch (error) {
            throw error;
        }
    };
}