import React, {useContext, useEffect} from 'react';
import {Grid} from "semantic-ui-react";
import {ProfileAvatar} from "../ProfileAvatar";
import {RouteComponentProps} from 'react-router';
import {RootStoreContext} from "../../../stores/rootStore";

interface ProfilePageProps {
    userId: string;
}

export const ProfilePage: React.FC<RouteComponentProps<ProfilePageProps>> = ({match}) => {
    const rootStore = useContext(RootStoreContext);
    const {user: loggedInUser} = rootStore.userStore;

    useEffect(() => {

    }, [match.params.userId])

    return (
        <Grid className="profile-page">
            <Grid.Column width={5}>
                <ProfileAvatar/>
            </Grid.Column>
        </Grid>
    );
};