import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import {ICommentFormValues, IPost, IPostFormValues} from "../models/post";
import agent from "../api/agent";

const POST_PAGE_SIZE = 10;

export default class GroupPostStore {
    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable saveLoadingPost = false;
    @observable loadingPosts = false;
    @observable loadingInitialPosts = true;
    @observable groupPosts: IPost[] = [];
    @observable isLastPage: boolean = false;
    @observable pageNumber = 0;

    @action createPost = async (post: IPostFormValues, groupId: string) => {
        this.saveLoadingPost = true;
        try {
            const createdPost = await agent.Post.createGroupPost(post, groupId);
            runInAction(() => {
                createdPost.countLike = 0;
                this.groupPosts.unshift(createdPost);
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
                this.groupPosts = this.groupPosts.filter(post => post.id !== postId);
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadGroupPosts = async (groupId: string) => {
        this.loadingInitialPosts = true;
        try {
            const createdPost = await agent.Post.getGroupPosts(groupId, POST_PAGE_SIZE, 0);
            runInAction(() => {
                this.groupPosts = createdPost.content;
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

    @action fetchMorePosts = async (groupId: string) => {
        try {
            const createdPost = await agent.Post.getGroupPosts(groupId, POST_PAGE_SIZE, this.pageNumber + 1);
            runInAction(() => {
                this.groupPosts = [...this.groupPosts, ...createdPost.content];
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
                let find = this.groupPosts.find(post => post.id === postId);
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
                let find = this.groupPosts.find(post => post.id === postId);
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
                const post = this.groupPosts.find(post => post.id === postId);
                post!.comments = [comment, ...post!.comments]
            })
        } catch (error) {
            console.log(error);
        }
    }
}