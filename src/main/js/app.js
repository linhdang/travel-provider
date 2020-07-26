'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import ProviderForm from "./components/provider-form";

class App extends React.Component {
    render() {
        return (
            <div className="App">
                <ProviderForm/>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)