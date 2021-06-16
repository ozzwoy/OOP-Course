import './App.css';
import {
	Route,
	NavLink,
	HashRouter
} from "react-router-dom";
import Login from "./components/login.jsx";
import Periodicals from "./components/periodicals.jsx";

function App() {
  return (
    <HashRouter>
      <div className="App">
        <div className="content">
            <Route path="/login" component={Login}/>
            <Route path="/periodicals" component={Periodicals}/>
            <Route exact path="/" component={Login}/>
        </div>
      </div>
    </HashRouter>
  );
}

export default App;
