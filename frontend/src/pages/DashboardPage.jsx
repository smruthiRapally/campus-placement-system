import { useEffect, useState } from 'react';
import api from '../api/api';

export default function DashboardPage() {
  const [stats, setStats] = useState({ users: 0, skills: 0, drives: 0, applications: 0 });

  useEffect(() => {
    api.get('/dashboard').then((res) => setStats(res.data)).catch(console.error);
  }, []);

  return (
    <div>
      <h1>Dashboard</h1>
      <div className="stats-grid">
        {Object.entries(stats).map(([key, value]) => (
          <div className="stat-card" key={key}>
            <h3>{key.toUpperCase()}</h3>
            <p>{value}</p>
          </div>
        ))}
      </div>
      <div className="card">
        <h3>Project modules</h3>
        <p>This starter includes authentication, users, skills, drives, applications, and dashboard analytics.</p>
      </div>
    </div>
  );
}
