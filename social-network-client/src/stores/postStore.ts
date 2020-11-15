import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {IPost, IPostFormValues} from "../models/post";
import agent from "../api/agent";
import {Page} from "../models/page";

const POST_PAGE_SIZE = 10;

export default class PostStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable saveLoadingPost = false;
    @observable loadingPosts = false;
    @observable loadingInitialPosts = true;
    @observable userPostsPage: Page<IPost[]> | null = null;
    @observable userPosts: IPost[] = [];

    @action createPost = async (post: IPostFormValues) => {
        this.saveLoadingPost = true;
        try {
            const createdPost = await agent.Post.create(post);
            runInAction(() => {
                this.userPosts.unshift(createdPost);
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.saveLoadingPost = false;
            })
        }
    }

    @action loadUserPosts = async (userId: string) => {
        this.loadingInitialPosts = true;
        try {
            const createdPost = await agent.Post.getUserPosts(userId, POST_PAGE_SIZE, 0);
            runInAction(() => {
                this.userPostsPage = createdPost;
                this.userPosts = createdPost.content;
            })
        } catch (error) {
            console.log(error);
        } finally {
            runInAction(() => {
                this.loadingInitialPosts = false;
            })
        }
    }

    @action fetchMorePosts = async (userId: string) => {
        try {
            const createdPost = await agent.Post.getUserPosts(userId, POST_PAGE_SIZE, this.userPostsPage!.number + 1);
            runInAction(() => {
                this.userPostsPage = createdPost;
                this.userPosts.push(...createdPost.content)
            })
        } catch (error) {
            console.log(error);
        }
    }
}