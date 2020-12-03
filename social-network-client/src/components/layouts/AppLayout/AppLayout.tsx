import React, {useContext, useEffect} from "react";
import "./AppLayout.scss";
import {Container} from "semantic-ui-react";
import {Sidebar} from "../../navigation/Sidebar";
import {Route, Switch} from "react-router-dom";
import {EditProfilePage, ProfilePage} from "../../../pages";
import * as Routes from "../../../util/routes";
import {FriendsPage} from "../../../pages/FriendsPage";
import {FeedPage} from "../../../pages/FeedPage";
import {Chat, ChatList} from "../../chat";
import {RootStoreContext} from "../../../stores/rootStore";
import {GroupFindPage} from "../../../pages/GroupFindPage";

/**
 * Main layout of the app, when user is authenticated
 */
export const AppLayout = () => {
    const rootStore = useContext(RootStoreContext);
    const {connectMessages} = rootStore.chatStore;

    useEffect(() => {
        connectMessages()
    }, [connectMessages])

    return (
        <Container className="main-layout">
            <Sidebar/>
            <div className="page-body">
                <div className="page-body">
                    <Switch>
                        <Route exact path={Routes.PROFILE} component={ProfilePage}/>
                        <Route exact path={Routes.EDIT} component={EditProfilePage}/>
                        <Route exact path={Routes.FRIENDS} component={FriendsPage}/>
                        <Route exact path={Routes.FEED} component={FeedPage}/>
                        <Route exact path={Routes.CHATS} component={ChatList}/>
                        <Route exact path={Routes.CHAT} component={Chat}/>
                        <Route exact path={Routes.GROUPS} component={GroupFindPage}/>
                    </Switch>
                </div>
            </div>
        </Container>
    );
};