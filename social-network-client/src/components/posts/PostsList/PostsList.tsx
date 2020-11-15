import React, {useContext, useEffect} from 'react';
import {RootStoreContext} from "../../../stores/rootStore";
import {Post} from "../Post";
import InfiniteScroll from "react-infinite-scroll-component";
import {observer} from "mobx-react-lite";
import {Dimmer, Loader, Segment} from "semantic-ui-react";
import "./PostsList.scss";

export interface PostListProps {
    userId: string;
}

export const PostsList = observer(({userId}: PostListProps) => {
    const rootStore = useContext(RootStoreContext);
    const {
        loadUserPosts,
        userPosts,
        fetchMorePosts,
        userPostsPage,
        loadingInitialPosts,
        like,
        unlike
    } = rootStore.postStore;

    useEffect(() => {
        loadUserPosts(userId);
    }, [userId])

    if (loadingInitialPosts) return (
        <Segment className="posts-loader">
            <Dimmer active inverted>
                <Loader inverted content='Loading posts'/>
            </Dimmer>
        </Segment>
    );

    return (
        <InfiniteScroll
            next={() => fetchMorePosts(userId)}
            hasMore={!userPostsPage!.isLast}
            loader={<h4>Loading...</h4>}
            dataLength={userPosts.length}
        >
            {userPosts && userPosts.map(post => (
                <Post key={post.id} post={post} like={like} unlike={unlike}/>
            ))}
        </InfiniteScroll>
    );
});