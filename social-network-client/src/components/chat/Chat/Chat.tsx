import React, {useContext, useEffect} from 'react';
import {Grid, Image, Segment} from "semantic-ui-react";
import {MessageCreateForm} from "../MessageCreateForm";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../../stores/rootStore";
import {ChatMessage} from "../ChatMessage";
import {observer} from "mobx-react-lite";
import "./Chat.scss";
import InfiniteScroll from "react-infinite-scroll-component";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {Link} from "react-router-dom";

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
        fetchMoreMessages,
        activeUserChat
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
                            {!messages.length && <Segment>Напишите сообщение первый!</Segment>}
                            {messages.map(message => (
                                <ChatMessage message={message} key={message.id}/>
                            ))}
                        </InfiniteScroll>
                    </div>
                    <MessageCreateForm recipientId={match.params.recipientId}/>
                </Segment>
            </Grid.Column>
            <Grid.Column width={5}>
                <Segment>
                    <Link to={`/id${activeUserChat?.id}`}>
                        <span
                            className="chat__profile-name">{activeUserChat?.firstName} {activeUserChat?.lastName}</span>
                        <Image avatar src={activeUserChat?.avatarUrl ? activeUserChat.avatarUrl : AvatarNotFound}/>
                    </Link>
                </Segment>
            </Grid.Column>
        </Grid>
    );
});