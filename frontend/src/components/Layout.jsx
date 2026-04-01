import { Link, Outlet, useNavigate } from 'react-router-dom';

export default function Layout() {
  const navigate = useNavigate();
  const userName = localStorage.getItem('fullName') || 'User';

  const logout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <h2>Campus Placement</h2>
        <p>Welcome, {userName}</p>
        <nav>
          <Link to="/">Dashboard</Link>
          <Link to="/users">Users</Link>
          <Link to="/skills">Skills</Link>
          <Link to="/drives">Drives</Link>
          <Link to="/applications">Applications</Link>
        </nav>
        <button className="danger" onClick={logout}>Logout</button>
      </aside>
      <main className="content">
        <Outlet />
      </main>
    </div>
  );
}
