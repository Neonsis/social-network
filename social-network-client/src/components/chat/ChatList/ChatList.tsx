import React, {useContext, useEffect} from 'react';
import {Grid, List} from "semantic-ui-react";
import {ChatListItem} from "../ChatListItem";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";

export const ChatList = observer(() => {
    const rootStore = useContext(RootStoreContext);
    const {
        chats,
        loadChats,
        loadingChats
    } = rootStore.chatStore;

    useEffect(() => {
        loadChats();
    }, [loadChats])

    if (loadingChats) return null;

    return (
        <Grid>
            <Grid.Column width={11}>
                <List celled>
                    {chats.map(user => (
                        <ChatListItem user={user} key={user.id}/>
                    ))}
                </List>
            </Grid.Column>
        </Grid>
    );
});
