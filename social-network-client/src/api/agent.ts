import axios, {AxiosResponse} from "axios";
import {IPhoto, IUser, IUserAuth, IUserDetails, IUserFormValues} from "../models/user";
import {IProfileDetails} from "../models/profile";
import {Page} from "../models/page";
import {IComment, ICommentFormValues, IPost, IPostFormValues} from "../models/post";
import {IMessage} from "../models/chat";
import {IGroup, IGroupDetails} from "../models/groups";

axios.defaults.baseURL = process.env.REACT_APP_API_URL;

axios.interceptors.request.use(
    config => {
        const token = window.localStorage.getItem("jwt");
        if (token) config.headers.Authorization = `Bearer ${token}`;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(undefined, error => {
    throw error.response;
});

const responseBody = (response: AxiosResponse) => response.data;

const requests = {
    get: (url: string) =>
        axios
            .get(url)
            .then(responseBody),
    post: (url: string, body: {}) =>
        axios
            .post(url, body)
            .then(responseBody),
    put: (url: string, body: {}) =>
        axios
            .put(url, body)
            .then(responseBody),
    del: (url: string) =>
        axios
            .delete(url)
            .then(responseBody),
    postForm: (url: string, file: File) => {
        let formData = new FormData();
        formData.append('file', file);
        formData.append('jsonBodyData',
            new Blob([JSON.stringify(formData)], {
                type: 'application/json'
            }));
        return axios
            .post(url, formData, {
                headers: {'Content-type': 'multipart/form-data'}
            })
            .then(responseBody);
    }
}

const User = {
    login: (user: IUserFormValues): Promise<IUserAuth> => requests.post("/auth/signin", user),
    logout: (): Promise<void> => requests.post("/auth/logout", {}),
    signup: (user: IUserFormValues): Promise<IUserAuth> => requests.post("/auth/signup", user),
    current: (): Promise<IUser> => requests.get("/users/me"),
    get: (userId: string): Promise<IUserDetails> => requests.get(`/users/${userId}`),
    profileDetails: (userId: string): Promise<IProfileDetails> => requests.get(`/profiles/${userId}`),
    saveDetails: (details: IProfileDetails): Promise<IProfileDetails> => requests.put(`/profiles`, details),
    uploadAvatar: (photo: File): Promise<IPhoto> => requests.postForm("/users/uploadAvatar", photo)
}

const Friendship = {
    getFriends: (userId: string, search: string, page: number, size: number)
        : Promise<Page<IUser[]>> => requests.get(`/users/${userId}/friends?size=${size}&page=${page}&search=${search}`),
    getFollowers: (userId: string, page: number, size: number)
        : Promise<Page<IUser[]>> => requests.get(`/users/${userId}/followers`),
    post: (friendId: string): Promise<void> => requests.post(`/friends/${friendId}`, {}),
    delete: (friendId: string): Promise<void> => requests.del(`/friends/${friendId}`)
}

const Post = {
    createUserPost: (post: IPostFormValues): Promise<IPost> => requests.post(`/users/posts/`, post),
    createGroupPost: (post: IPostFormValues, groupId: string): Promise<IPost> => requests.post(`/communities/${groupId}/posts/`, post),
    getUserPosts: (userId: string, size: number, page: number)
        : Promise<Page<IPost[]>> => requests.get(`/users/${userId}/posts?page=${page}&size=${size}`),
    getGroupPosts: (groupId: string, size: number, page: number)
        : Promise<Page<IPost[]>> => requests.get(`/communities/${groupId}/posts?page=${page}&size=${size}`),
    feed: (size: number, page: number): Promise<Page<IPost[]>> => requests.get(`/users/feed?page=${page}&size=${size}`),
    delete: (postId: string): Promise<void> => requests.del(`/posts/${postId}`),
    like: (postId: string): Promise<void> => requests.post(`/posts/${postId}/like`, {}),
    unlike: (postId: string): Promise<void> => requests.post(`/posts/${postId}/unlike`, {}),
    addComment: (postId: string, comment: ICommentFormValues): Promise<IComment> => requests.post(`/posts/${postId}/comments`, comment)
}

const Chat = {
    getChats: (): Promise<Page<IUser[]>> => requests.get(`/me/conversations`),
    getMessages: (recipientId: string, size: number, page: number)
        : Promise<Page<IMessage[]>> => requests.get(`/messages/${recipientId}?page=${page}&size=${size}`)
}

const Group = {
    getGroup: (groupId: string): Promise<IGroupDetails> => requests.get(`/communities/${groupId}`),
    getGroups: (page: number, size: number, sort: string, search: string)
        : Promise<Page<IGroup[]>> => requests.get(`/communities?page=${page}&size=${size}&search=${search}&sort=${sort}`),
    getUserGroups: (userId: string, search: string, page: number, size: number)
        : Promise<Page<IGroup[]>> => requests.get(`/users/${userId}/communities?page=${page}&size=${size}&search=${search}`),
    getModeratorGroups: (moderatorId: string, search: string, page: number, size: number)
        : Promise<Page<IGroup[]>> => requests.get(`/users/${moderatorId}/moderator/communities?page=${page}&size=${size}&search=${search}`),
    getGroupFollowers: (groupId: string, page: number, size: number)
        : Promise<Page<IUser[]>> => requests.get(`/communities/${groupId}/followers`),
    createGroup: (title: string): Promise<IGroup> => requests.post("/communities", {title}),
    follow: (groupId: string): Promise<void> => requests.post(`/communities/${groupId}/follow`, {}),
    unfollow: (groupId: string): Promise<void> => requests.post(`/communities/${groupId}/unfollow`, {}),
}

export default {
    User,
    Friendship,
    Post,
    Chat,
    Group
};