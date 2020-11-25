import React, {useContext, useEffect} from 'react';
import {Grid, Segment} from "semantic-ui-react";
import {MessageCreateForm} from "../MessageCreateForm";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../../stores/rootStore";
import {ChatMessage} from "../ChatMessage";
import {observer} from "mobx-react-lite";
import "./Chat.scss";
import InfiniteScroll from "react-infinite-scroll-component";

export interface ChatProps {
    recipientId: string;
}

export const Chat = observer(({match}: RouteComponentProps<ChatProps>) => {
    const rootStore = useContext(RootStoreContext);
    const {
        loadMessages,
        loadingMessages,
        loadActiveUser,
        messages,
        isLastMessage,
        fetchMoreMessages
    } = rootStore.chatStore;

    useEffect(() => {
        loadActiveUser(match.params.recipientId);
        loadMessages(match.params.recipientId);
    }, [match.params.recipientId, loadActiveUser, loadMessages])

    if (loadingMessages) {
        return null;
    }

    return (
        <Grid>
            <Grid.Column width={11}>
                <Segment className="chat">
                    <div id="chat-feed">
                        <InfiniteScroll
                            next={() => fetchMoreMessages(match.params.recipientId)}
                            style={{display: 'flex', flexDirection: 'column-reverse', overflow: 'hidden'}}
                            inverse={true}
                            hasMore={!isLastMessage}
                            loader={<h4>Loading...</h4>}
                            dataLength={messages.length}
                            scrollableTarget="chat-feed"
                        >
                            {messages.map(message => (
                                <ChatMessage message={message} key={message.id}/>
                            ))}
                        </InfiniteScroll>
                    </div>
                    <MessageCreateForm recipientId={match.params.recipientId}/>
                </Segment>
            </Grid.Column>
        </Grid>
    );
});