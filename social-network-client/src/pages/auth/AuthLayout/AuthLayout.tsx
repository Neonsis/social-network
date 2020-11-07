import React from 'react';
import {Container} from "semantic-ui-react";
import {Redirect, Route, Switch} from 'react-router-dom';
import * as Routes from "../../../util/routes";
import {HomePage} from "../HomePage";


/**
 * Main Layout for the app, when user isn't authenticated
 */
export const AuthLayout = () => {
    return (
        <Container>
            <Switch>
                <Route exact path={Routes.HOME} render={() => <HomePage/>}/>
                <Redirect to={Routes.HOME}/>
            </Switch>
        </Container>
    );
};