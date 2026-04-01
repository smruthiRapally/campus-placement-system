import { useEffect, useState } from 'react';
import api from '../api/api';

export default function DrivesPage() {
  const [drives, setDrives] = useState([]);
  const [form, setForm] = useState({ companyName: '', jobRole: '', location: '', minCgpa: '', requiredSkills: '', driveDate: '', status: '', description: '' });

  const load = async () => {
    const { data } = await api.get('/drives');
    setDrives(data);
  };

  useEffect(() => { load(); }, []);

  const submit = async (e) => {
    e.preventDefault();
    await api.post('/drives', { ...form, minCgpa: form.minCgpa ? Number(form.minCgpa) : null });
    setForm({ companyName: '', jobRole: '', location: '', minCgpa: '', requiredSkills: '', driveDate: '', status: '', description: '' });
    load();
  };

  return (
    <div>
      <h1>Company Drives</h1>
      <form className="card form-grid" onSubmit={submit}>
        <input placeholder="Company Name" value={form.companyName} onChange={(e) => setForm({ ...form, companyName: e.target.value })} />
        <input placeholder="Job Role" value={form.jobRole} onChange={(e) => setForm({ ...form, jobRole: e.target.value })} />
        <input placeholder="Location" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} />
        <input placeholder="Minimum CGPA" value={form.minCgpa} onChange={(e) => setForm({ ...form, minCgpa: e.target.value })} />
        <input placeholder="Required Skills" value={form.requiredSkills} onChange={(e) => setForm({ ...form, requiredSkills: e.target.value })} />
        <input type="date" value={form.driveDate} onChange={(e) => setForm({ ...form, driveDate: e.target.value })} />
        <input placeholder="Status" value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })} />
        <textarea placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
        <button type="submit">Add Drive</button>
      </form>
      <div className="table-wrap">
        <table>
          <thead><tr><th>ID</th><th>Company</th><th>Role</th><th>Date</th><th>Location</th><th>Status</th></tr></thead>
          <tbody>
            {drives.map(drive => (
              <tr key={drive.id}>
                <td>{drive.id}</td>
                <td>{drive.companyName}</td>
                <td>{drive.jobRole}</td>
                <td>{drive.driveDate}</td>
                <td>{drive.location}</td>
                <td>{drive.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
