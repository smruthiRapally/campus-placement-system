import { useEffect, useState } from 'react';
import api from '../api/api';

export default function SkillsPage() {
  const [skills, setSkills] = useState([]);
  const [form, setForm] = useState({ userId: '', name: '', level: '', certification: '' });

  const load = async () => {
    const { data } = await api.get('/skills');
    setSkills(data);
  };

  useEffect(() => { load(); }, []);

  const submit = async (e) => {
    e.preventDefault();
    await api.post(`/skills/user/${form.userId}`, {
      name: form.name,
      level: form.level,
      certification: form.certification
    });
    setForm({ userId: '', name: '', level: '', certification: '' });
    load();
  };

  return (
    <div>
      <h1>Skills</h1>
      <form className="card form-grid" onSubmit={submit}>
        <input placeholder="User ID" value={form.userId} onChange={(e) => setForm({ ...form, userId: e.target.value })} />
        <input placeholder="Skill Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
        <input placeholder="Level" value={form.level} onChange={(e) => setForm({ ...form, level: e.target.value })} />
        <input placeholder="Certification" value={form.certification} onChange={(e) => setForm({ ...form, certification: e.target.value })} />
        <button type="submit">Add Skill</button>
      </form>
      <div className="table-wrap">
        <table>
          <thead><tr><th>ID</th><th>Name</th><th>Level</th><th>Certification</th><th>User</th></tr></thead>
          <tbody>
            {skills.map(skill => (
              <tr key={skill.id}>
                <td>{skill.id}</td>
                <td>{skill.name}</td>
                <td>{skill.level}</td>
                <td>{skill.certification}</td>
                <td>{skill.user?.fullName}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
