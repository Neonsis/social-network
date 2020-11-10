import React, {useContext, useEffect} from 'react';
import {Grid} from "semantic-ui-react";
import {ProfileAvatar} from "../ProfileAvatar";
import {RouteComponentProps} from 'react-router';
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {LoadingComponent} from "../../../components/layot/LoadingComponent";
import {ProfileInfo} from "../ProfileInfo";

interface ProfilePageProps {
    userId: string;
}

export const ProfilePage = observer<RouteComponentProps<ProfilePageProps>>(({match}) => {
    const rootStore = useContext(RootStoreContext);
    const {loadingPage, userProfile, loadUser} = rootStore.profileStore;

    useEffect(() => {
        loadUser(match.params.userId)
    }, [match.params.userId, loadUser])

    if (loadingPage) return <LoadingComponent content="Loading app..."/>

    return (
        <Grid>
            <Grid.Column width={5}>
                <ProfileAvatar
                    isFriend={userProfile!.isFriend}
                    isLoggedInUser={userProfile!.isLoggedInUser}
                    isPendingFriendship={userProfile!.isPendingFriendship}
                />
            </Grid.Column>
            <Grid.Column width={11}>
                <ProfileInfo
                    firstName={userProfile!.firstName}
                    lastName={userProfile!.lastName}
                    id={match.params.userId}
                />
            </Grid.Column>
        </Grid>
    );
});