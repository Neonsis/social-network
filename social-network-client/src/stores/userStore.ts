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
                this.user = user;
            })
        } catch (error) {
            console.log(error);
        } finally {
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
                this.user = user;
            });
            this.rootStore.commonStore.setToken(user.token);
        } catch (error) {
            throw error;
        } finally {
            runInAction(() => {
                this.loading = false;
            });
        }
    };

    @action register = async (values: IUserFormValues) => {
        try {
            this.loading = true;
            const user = await agent.User.signup(values);
            runInAction(() => {
                this.user = user;
            });
            this.rootStore.commonStore.setToken(user.token);
        } catch (error) {
            throw error;
        } finally {
            runInAction(() => {
                this.loading = false;
            });
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