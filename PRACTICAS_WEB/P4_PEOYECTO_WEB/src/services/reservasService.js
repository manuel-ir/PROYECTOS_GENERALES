import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const rutaReservas = path.join(__dirname, '../data/reservas.json');

export function leerReservas() {
    const datos = fs.readFileSync(rutaReservas, 'utf8');
    return JSON.parse(datos);
}

export function guardarReservas(reservas) {
    const texto = JSON.stringify(reservas, null, 2);
    fs.writeFile(rutaReservas, texto, (err) => {
        if (err) {
            console.error('Error al guardar:', err);
        }
    });
}