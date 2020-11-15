import React, {useContext, useEffect} from 'react';
import {RootStoreContext} from "../../../stores/rootStore";
import {Post} from "../Post";
import InfiniteScroll from "react-infinite-scroll-component";
import {observer} from "mobx-react-lite";

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
        loadingInitialPosts
    } = rootStore.postStore;

    useEffect(() => {
        loadUserPosts(userId);
    }, [])

    if (loadingInitialPosts) return null;

    console.log(userPosts.length)

    return (
        <InfiniteScroll
            next={() => fetchMorePosts(userId)}
            hasMore={!userPostsPage!.isLast}
            loader={<h4>Loading...</h4>}
            dataLength={userPosts.length}
        >
            {userPosts.map(post => (
                <Post key={post.id} post={post}/>
            ))}
        </InfiniteScroll>
    );
});