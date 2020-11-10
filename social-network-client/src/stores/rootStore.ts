import {configure} from "mobx";
import {createContext} from "react";
import UserStore from "./userStore";
import CommonStore from "./commonStore";
import ProfileStore from "./profileStore";

configure({enforceActions: "always"});

export class RootStore {
    userStore: UserStore;
    commonStore: CommonStore;
    profileStore: ProfileStore;

    constructor() {
        this.userStore = new UserStore(this);
        this.commonStore = new CommonStore(this);
        this.profileStore = new ProfileStore(this);
    }
}

export const RootStoreContext = createContext(new RootStore());