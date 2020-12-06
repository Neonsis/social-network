import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";
import {IGroup, IGroupDetails} from "../models/groups";
import {history} from '../';
import {IPost} from "../models/post";
import {IUser} from "../models/user";
import {Page} from "../models/page";


const GROUPS_PAGE_SIZE = 10;

export default class GroupsStore {

    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable page = 0;
    @observable isLastGroups = false;
    @observable userGroups: IGroup[] = [];
    @observable createGroupLoader = false;
    @observable group: IGroupDetails | null = null;
    @observable followLoader = false;
    @observable groupLoader = true;
    @observable groupPost: IPost[] = [];
    @observable groupFollowersLoader = true;
    @observable groupFollowers: Page<IUser[]> | null = null;

    @action loadGroup = async (groupId: string) => {
        this.groupLoader = true;
        try {
            const group = await agent.Group.getGroup(groupId);
            runInAction(() => {
                this.group = group;
                this.groupLoader = false;
            })
        } catch (error) {
            runInAction(() => {
                this.groupLoader = false;
            })
            console.log(error);
        }
    }

    @action loadUserGroups = async (search: string) => {
        this.page = 0;
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getUserGroups(id!, search, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = userGroups.content;
                this.isLastGroups = userGroups.last;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadModeratorGroups = async (search: string) => {
        this.page = 0;
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getModeratorGroups(id!, search, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = userGroups.content;
                this.isLastGroups = userGroups.last;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadPopularGroups = async (search: string) => {
        try {
            const popularGroups = await agent.Group.getGroups(this.page, GROUPS_PAGE_SIZE, "followersCount", search);
            runInAction(() => {
                this.userGroups = popularGroups.content;
                this.isLastGroups = popularGroups.last;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action fetchMoreUserGroups = async (search: string,) => {
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getUserGroups(id!, search, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = [...this.userGroups, ...userGroups.content];
                this.isLastGroups = userGroups.last;
                this.page += 1;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action creatGroup = async (title: string) => {
        this.createGroupLoader = true;
        try {
            const group = await agent.Group.createGroup(title);
            runInAction(() => {
                this.createGroupLoader = false;
                history.push(`/group${group.id}`);
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action follow = async (groupId: string) => {
        this.followLoader = false;
        try {
            await agent.Group.follow(groupId);
            runInAction(() => {
                this.group!.isUserFollow = true;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action unfollow = async (groupId: string) => {
        this.followLoader = false;
        try {
            await agent.Group.follow(groupId);
            runInAction(() => {
                this.group!.isUserFollow = false;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadGroupFollowers = async (groupId: string) => {
        this.groupFollowersLoader = true;
        try {
            const followers = await agent.Group.getGroupFollowers(groupId, 0, 6);
            runInAction(() => {
                this.groupFollowers = followers;
                this.groupFollowersLoader = false;
            })
        } catch (error) {
            runInAction(() => {
                this.groupFollowersLoader = false;
            })
            console.log(error);
        }
    }
}