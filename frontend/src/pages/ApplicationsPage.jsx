import { useEffect, useState } from 'react';
import api from '../api/api';

export default function ApplicationsPage() {
  const [applications, setApplications] = useState([]);
  const [form, setForm] = useState({ studentId: '', driveId: '', status: 'APPLIED', feedback: '' });

  const load = async () => {
    const { data } = await api.get('/applications');
    setApplications(data);
  };

  useEffect(() => { load(); }, []);

  const submit = async (e) => {
    e.preventDefault();
    await api.post('/applications', {
      studentId: Number(form.studentId),
      driveId: Number(form.driveId),
      status: form.status,
      feedback: form.feedback
    });
    setForm({ studentId: '', driveId: '', status: 'APPLIED', feedback: '' });
    load();
  };

  return (
    <div>
      <h1>Applications</h1>
      <form className="card form-grid" onSubmit={submit}>
        <input placeholder="Student ID" value={form.studentId} onChange={(e) => setForm({ ...form, studentId: e.target.value })} />
        <input placeholder="Drive ID" value={form.driveId} onChange={(e) => setForm({ ...form, driveId: e.target.value })} />
        <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
          <option>APPLIED</option>
          <option>SHORTLISTED</option>
          <option>INTERVIEW_SCHEDULED</option>
          <option>SELECTED</option>
          <option>REJECTED</option>
        </select>
        <input placeholder="Feedback" value={form.feedback} onChange={(e) => setForm({ ...form, feedback: e.target.value })} />
        <button type="submit">Submit Application</button>
      </form>
      <div className="table-wrap">
        <table>
          <thead><tr><th>ID</th><th>Student</th><th>Company</th><th>Status</th><th>Applied At</th></tr></thead>
          <tbody>
            {applications.map(app => (
              <tr key={app.id}>
                <td>{app.id}</td>
                <td>{app.student?.fullName}</td>
                <td>{app.drive?.companyName}</td>
                <td>{app.status}</td>
                <td>{app.appliedAt}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
