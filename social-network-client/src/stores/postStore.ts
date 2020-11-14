import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {IPost, IPostFormValues} from "../models/post";
import agent from "../api/agent";

export default class PostStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable saveLoadingPost = false;
    @observable userPosts: IPost[] = [];

    @action createPost = async (post: IPostFormValues) => {
        this.saveLoadingPost = true;
        try {
            const createdPost = await agent.Post.create(post);
            runInAction(() => {
                this.userPosts.push(createdPost);
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.saveLoadingPost = false;
            })
        }
    }
}