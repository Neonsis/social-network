import React, {useContext, useEffect} from "react";
import {Grid} from "semantic-ui-react";
import {ProfileAvatar} from "../ProfileAvatar";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {ProfilesSection} from "../../../components/profiles/ProfilesSection";
import {ProfileInfo} from "../ProfileInfo";

interface ProfilePageProps {
    userId: string;
}

export const ProfilePage = observer<RouteComponentProps<ProfilePageProps>>(({match}) => {
    const rootStore = useContext(RootStoreContext);
    const {
        loadingPage,
        user,
        loadUser,
        profileDetails,
        loadingProfileDetails
    } = rootStore.profileStore;
    const {
        loadProfileFriends,
        profileFriends,
        addToFriends,
        deleteFriendship,
        cancelFriendship,
        confirmFriendship,
        loadingFriends
    } = rootStore.friendshipStore;
    const {loadProfileDetails} = rootStore.profileStore;

    useEffect(() => {
        loadUser(match.params.userId);
        loadProfileFriends(match.params.userId)
        loadProfileDetails(match.params.userId);
    }, [match.params.userId, loadUser, loadProfileFriends])

    return (
        <Grid>
            <Grid.Column width={5}>
                <ProfileAvatar
                    loadingPage={loadingPage}
                    id={match.params.userId}
                    isFriend={user?.isFriend}
                    isLoggedInUser={user?.isLoggedInUser}
                    isPendingFriendship={user?.isPendingFriendship}
                    isFollower={user?.isFollower}
                    addToFriends={addToFriends}
                    deleteFriendship={deleteFriendship}
                    cancelFriendship={cancelFriendship}
                    confirmFriendship={confirmFriendship}
                />
                <ProfilesSection
                    header="Друзья"
                    profiles={profileFriends!}
                    loading={loadingFriends}
                />
            </Grid.Column>
            <Grid.Column width={11}>
                <ProfileInfo
                    firstName={user?.firstName}
                    lastName={user?.lastName}
                    profileDetails={profileDetails!}
                    loading={loadingProfileDetails}
                />
            </Grid.Column>
        </Grid>
    );
});