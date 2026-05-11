import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const rutaUsuarios = path.join(__dirname, '../data/usuarios.json');

export function leerUsuarios() {
    const datos = fs.readFileSync(rutaUsuarios, 'utf8');
    return JSON.parse(datos);
}