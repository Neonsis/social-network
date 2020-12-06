import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {ICommentFormValues, IPost} from "../models/post";
import agent from "../api/agent";

const POST_PAGE_SIZE = 10;

export default class FeedStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable feedPosts: IPost[] = [];
    @observable loadFeedPosts = true;
    @observable page = 0;
    @observable isLastPage = false;

    @action getFeedPosts = async () => {
        this.loadFeedPosts = true;
        this.page = 0;
        try {
            let posts = await agent.Post.feed(POST_PAGE_SIZE, this.page);
            runInAction(() => {
                this.feedPosts = posts.content;
                this.isLastPage = posts.last;
                this.loadFeedPosts = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadFeedPosts = false;
            })
        }
    }

    @action fetchMorePosts = async () => {
        this.loadFeedPosts = true;
        try {
            let post = await agent.Post.feed(POST_PAGE_SIZE, this.page + 1);
            runInAction(() => {
                this.feedPosts = [...this.feedPosts, ...post.content];
                this.isLastPage = post.last;
                this.page = post.number;
                this.loadFeedPosts = false;
            })
        } catch (error) {
            console.log(error);
            runInAction(() => {
                this.loadFeedPosts = false;
            })
        }
    }

    @action like = async (postId: string) => {
        try {
            await agent.Post.like(postId);
            runInAction(() => {
                let find = this.feedPosts.find(post => post.id === postId);
                find!.isLiked = true;
                find!.countLike++;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action unlike = async (postId: string) => {
        try {
            await agent.Post.unlike(postId);
            runInAction(() => {
                let find = this.feedPosts.find(post => post.id === postId);
                find!.isLiked = false;
                find!.countLike--;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action addComment = async (postId: string, values: ICommentFormValues) => {
        try {
            const comment = await agent.Post.addComment(postId, values);
            runInAction(() => {
                const post = this.feedPosts.find(post => post.id === postId);
                post!.comments = [comment, ...post!.comments]
            })
        } catch (error) {
            console.log(error);
        }
    }
}