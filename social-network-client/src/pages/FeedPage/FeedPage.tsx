import React, {useContext, useEffect} from 'react';
import {observer} from "mobx-react-lite";
import {RootStoreContext} from "../../stores/rootStore";
import {Post} from "../../components/posts/Post";
import InfiniteScroll from "react-infinite-scroll-component";
import {Grid, Segment} from "semantic-ui-react";
import "./FeedPage.scss";

export const FeedPage = observer(() => {
    const rootStore = useContext(RootStoreContext);

    const {
        getFeedPosts,
        fetchMorePosts,
        loadFeedPosts,
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

    if (loadFeedPosts) return (
        <Segment textAlign="center">
            Загрузка постов...
        </Segment>
    );

    return (
        <Grid>
            <Grid.Column>
                <InfiniteScroll
                    next={fetchMorePosts}
                    hasMore={!isLastPage}
                    loader={<h4>Loading...</h4>}
                    dataLength={feedPosts.length}
                >
                    {!feedPosts.length && (
                        <Segment textAlign="center">
                            Добавьте друзей или вступите в группы, чтобы у вас появилсь посты
                        </Segment>
                    )}
                    <div className="feed-content">
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
                    </div>
                </InfiniteScroll>
            </Grid.Column>
        </Grid>
    );
});