import React, {useContext, useEffect} from 'react';
import {observer} from "mobx-react-lite";
import {RootStoreContext} from "../../stores/rootStore";
import {Post} from "../../components/posts/Post";
import InfiniteScroll from "react-infinite-scroll-component";

export const FeedPage = observer(() => {
    const rootStore = useContext(RootStoreContext);

    const {
        getFeedPosts,
        fetchMorePosts,
        feedPosts,
        isLastPage,
        like,
        unlike,
        addComment
    } = rootStore.feedStore;

    const {user} = rootStore.userStore

    useEffect(() => {
        getFeedPosts();
    }, [])

    return (
        <InfiniteScroll
            next={() => fetchMorePosts()}
            hasMore={!isLastPage}
            loader={<h4>Loading...</h4>}
            dataLength={feedPosts.length}
        >
            {feedPosts && feedPosts.map(post => (
                <Post
                    key={post.id}
                    post={post}
                    like={like}
                    unlike={unlike}
                    loggedInUserId={user!.id}
                    onDelete={(id) => console.log(id)}
                    addComment={addComment}
                />
            ))}
        </InfiniteScroll>
    );
});