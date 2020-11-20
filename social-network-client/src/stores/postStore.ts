import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {ICommentFormValues, IPost, IPostFormValues} from "../models/post";
import agent from "../api/agent";

const POST_PAGE_SIZE = 10;

export default class PostStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable saveLoadingPost = false;
    @observable loadingPosts = false;
    @observable loadingInitialPosts = true;
    @observable userPosts: IPost[] = [];
    @observable isLastPage: boolean = false;
    @observable pageNumber = 0;

    @action createPost = async (post: IPostFormValues) => {
        this.saveLoadingPost = true;
        try {
            const createdPost = await agent.Post.create(post);
            runInAction(() => {
                createdPost.countLike = 0;
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

    @action deletePost = async (postId: string) => {
        try {
            await agent.Post.delete(postId);
            runInAction(() => {
                this.userPosts = this.userPosts.filter(post => post.id !== postId);
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadUserPosts = async (userId: string) => {
        this.loadingInitialPosts = true;
        try {
            const createdPost = await agent.Post.getUserPosts(userId, POST_PAGE_SIZE, 0);
            runInAction(() => {
                this.userPosts = createdPost.content;
                this.isLastPage = createdPost.last;
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
            const createdPost = await agent.Post.getUserPosts(userId, POST_PAGE_SIZE, this.pageNumber + 1);
            runInAction(() => {
                this.userPosts = [...this.userPosts, ...createdPost.content];
                this.isLastPage = createdPost.last;
                this.pageNumber = createdPost.number;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action like = async (postId: string) => {
        try {
            await agent.Post.like(postId);
            runInAction(() => {
                let find = this.userPosts.find(post => post.id === postId);
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
                let find = this.userPosts.find(post => post.id === postId);
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
                const post = this.userPosts.find(post => post.id === postId);
                post!.comments = [comment, ...post!.comments]
            })
        } catch (error) {
            console.log(error);
        }
    }
}