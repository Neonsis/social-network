import {RootStore} from "./rootStore";
import {action, observable, runInAction} from "mobx";
import agent from "../api/agent";
import {IGroup, IGroupDetails} from "../models/groups";
import {history} from '../';
import {IPost} from "../models/post";

const GROUPS_PAGE_SIZE = 10;

export default class GroupsStore {

    rootStore: RootStore;

    constructor(rootStore: RootStore) {
        this.rootStore = rootStore;
    }

    @observable page = 0;
    @observable isLastUserGroups = false;
    @observable userGroups: IGroup[] = [];
    @observable createGroupLoader = false;
    @observable group: IGroupDetails | null = null;
    @observable followLoader = false;
    @observable groupPost: IPost[] = [];

    @action loadGroup = async (groupId: string) => {
        try {
            const group = await agent.Group.getGroup(groupId);
            runInAction(() => {
                this.group = group;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadUserGroups = async () => {
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getUserGroups(id!, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = userGroups.content;
                this.isLastUserGroups = userGroups.last;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action loadModeratorGroups = async () => {
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getModeratorGroups(id!, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = userGroups.content;
                this.isLastUserGroups = userGroups.last;
            })
        } catch (error) {
            console.log(error);
        }
    }

    @action fetchMoreUserGroups = async () => {
        const id = this.rootStore.userStore.user?.id;
        try {
            const userGroups = await agent.Group.getUserGroups(id!, this.page, GROUPS_PAGE_SIZE);
            runInAction(() => {
                this.userGroups = [...this.userGroups, ...userGroups.content];
                this.isLastUserGroups = userGroups.last;
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
}