import {RootStore} from "./rootStore";
import {action} from "mobx";

export default class ProfileStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @action loadProfile = async () => {

    }
}