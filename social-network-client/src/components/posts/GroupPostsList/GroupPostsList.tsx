import React, {useContext, useEffect} from 'react';
import InfiniteScroll from "react-infinite-scroll-component";
import {Post} from "../Post";
import {RootStoreContext} from "../../../stores/rootStore";
import {Dimmer, Loader, Segment} from "semantic-ui-react";
import {observer} from "mobx-react-lite";

export interface GroupPostsListProps {
    groupId: string;
}

export const GroupPostsList = observer(({groupId}: GroupPostsListProps) => {
    const rootStore = useContext(RootStoreContext);
    const {
        loadGroupPosts,
        fetchMorePosts,
        loadingInitialPosts,
        like,
        groupPosts,
        unlike,
        isLastPage,
        deletePost,
        addComment
    } = rootStore.groupPostStore;
    const {
        user
    } = rootStore.userStore;

    useEffect(() => {
        loadGroupPosts(groupId);
    }, [groupId, loadGroupPosts])

    if (loadingInitialPosts) return (
        <Segment className="posts-loader">
            <Dimmer active inverted>
                <Loader inverted content="Loading posts"/>
            </Dimmer>
        </Segment>
    );

    return (
        <InfiniteScroll
            next={() => fetchMorePosts(groupId)}
            hasMore={!isLastPage}
            loader={<h4>Loading...</h4>}
            dataLength={groupPosts.length}
        >
            {groupPosts && groupPosts.map(post => (
                <Post
                    key={post.id}
                    post={post}
                    like={like}
                    unlike={unlike}
                    loggedInUserId={user!.id}
                    onDelete={deletePost}
                    addComment={addComment}
                />
            ))}
        </InfiniteScroll>
    );
});
