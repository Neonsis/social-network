import React from "react";
import "./HomePage.scss";
import {SignUpForm} from "../SignUpForm";

export const HomePage = () => {
    return (
        <div className="home-page">
            <div className="home__promo">

            </div>
            <div className="home__auth">
                <SignUpForm/>
            </div>
        </div>
    );
};
