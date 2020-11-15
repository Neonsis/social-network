import React from "react";
import "./AppLayout.scss";
import {Container, Grid} from "semantic-ui-react";
import {Sidebar} from "../../navigation/Sidebar";
import {Route, Switch} from "react-router-dom";
import {EditProfilePage, ProfilePage} from "../../../pages";
import * as Routes from "../../../util/routes";

/**
 * Main layout of the app, when user is authenticated
 */
export const AppLayout = () => {
    return (
        <Container className="main-layout">
            <Grid>
                <Grid.Column width={3}>
                    <Sidebar/>
                </Grid.Column>
                <Grid.Column width={13}>
                    <div className="page-body">
                        <Switch>
                            <Route exact path={Routes.PROFILE} component={ProfilePage}/>
                            <Route exact path={Routes.EDIT} component={EditProfilePage}/>
                        </Switch>
                    </div>
                </Grid.Column>
            </Grid>
        </Container>
    );
};