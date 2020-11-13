import React from "react";
import {Container, Grid} from "semantic-ui-react";
import {Sidebar} from "../../navigation/Sidebar";
import {Route, Switch} from "react-router-dom";
import "./AppLayout.scss";
import * as Routes from "../../../util/routes";
import {ProfilePage} from "../../../pages/profile/ProfilePage";

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
                        </Switch>
                    </div>
                </Grid.Column>
            </Grid>
        </Container>
    );
};