import React from "react";
import "./HomePage.scss";
import {SignInForm, SignUpForm} from "../";
import {Header} from "semantic-ui-react";

export const HomePage = () => {
    return (
        <div className="home-page">
            <div className="home__promo">
                <Header as="h2" textAlign="center" size="huge">
                    Connect with friends and the world around you.
                    <Header.Subheader>
                        See photos and updates from your friends.
                    </Header.Subheader>
                    <Header.Subheader>
                        Follow your interests.
                    </Header.Subheader>
                    <Header.Subheader>
                        Hear what people are talking about.
                    </Header.Subheader>
                </Header>
            </div>
            <div className="home__auth">
                <SignInForm/>
                <SignUpForm/>
            </div>
        </div>
    );
};
