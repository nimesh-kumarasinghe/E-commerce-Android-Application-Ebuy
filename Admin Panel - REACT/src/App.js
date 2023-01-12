import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router,Routes,Route} from "react-router-dom";
import Login from './pages/login';
import Signup from './pages/signup';
import Cookies from 'universal-cookie';
import MyAccount from './pages/myaccount';
import SideBar from './components/sidebar';
import Dashboard from './pages/dashboard';
import Manageproducts from './pages/manageproducts';
import ManageOrders from './pages/manageorders';
import Logout from './pages/logout';

function App() {
  const cookies = new Cookies();
  const logged = cookies.get("logged");
  return (
    <Router>
      <div className="container">
        <div id="sidebar">
          {logged ? (<SideBar/>) : (<Login/>)}
        </div>
        <div id="main-content">
          <Routes>
            <Route exact path="/" element={logged ? (<MyAccount/>) : (<Login/>)}/>
            <Route exact path="signup" element={logged ? (<MyAccount/>) : (<Signup/>)}/>
            <Route exact path="/dashboard" element={logged ? (<Dashboard/>) : (<Login/>)}/>
            <Route exact path="/manage-products" element={logged ? (<Manageproducts/>) : (<Login/>)}/>
            <Route exact path="/manage-orders" element={logged ? (<ManageOrders/>) : (<Login/>)}/>
            <Route exact path="/my-account" element={logged ? (<MyAccount/>) : (<Login/>)}/>
            <Route exact path="/logout" element={logged ? (<Logout/>) : (<Login/>)}/>
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
