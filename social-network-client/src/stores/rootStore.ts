import {configure} from "mobx";
import {createContext} from "react";
import UserStore from "./userStore";
import CommonStore from "./commonStore";
import ProfileStore from "./profileStore";
import FriendshipStore from "./friendshipStore";
import PostStore from "./postStore";
import FriendsStore from "./friendsStore";
import FeedStore from "./feedStore";
import ChatStore from "./chatStore";

configure({enforceActions: "always"});

export class RootStore {
    userStore: UserStore;
    commonStore: CommonStore;
    profileStore: ProfileStore;
    friendshipStore: FriendshipStore;
    postStore: PostStore;
    friendsStore: FriendsStore;
    feedStore: FeedStore;
    chatStore: ChatStore;

    constructor() {
        this.userStore = new UserStore(this);
        this.commonStore = new CommonStore(this);
        this.profileStore = new ProfileStore(this);
        this.friendshipStore = new FriendshipStore(this);
        this.postStore = new PostStore(this);
        this.friendsStore = new FriendsStore(this);
        this.feedStore = new FeedStore(this);
        this.chatStore = new ChatStore(this);
    }
}

export const RootStoreContext = createContext(new RootStore());