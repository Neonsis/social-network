import {RootStore} from "./rootStore";
import {action, runInAction} from "mobx";
import agent from "../api/agent";

export default class ImageStore {

    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @action uploadAvatar = async (file: File) => {
        try {
            const photo = await agent.User.uploadAvatar(file);
            runInAction(() => {
                this.rootStore.userStore.user!.avatar = photo;
            })
        } catch (error) {
            console.log(error);
        }
    }

}