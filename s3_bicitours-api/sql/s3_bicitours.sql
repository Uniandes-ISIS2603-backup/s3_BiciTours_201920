delete from BlogEntity;
delete from BlogEntity_UsuarioEntity;
delete from UsuarioEntity; 
delete from SeguroEntity;
delete from ComentarioEntity;
delete from TourEntity;
delete from FotoEntity;

insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (0, 'D:\', 'G:\', 'A', 'E', 10);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (1, 'D:\', 'G:\', 'B', 'F', 11);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (2, 'C:\', 'F:\', 'C', 'G', 12);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (6, 'C:\', 'F:\', 'D', 'H', 16);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (79, 'C:\', 'F:\', 'D', 'H', 16);

insert into TourEntity (id, nombre, lugar, descripcion, duracion, costo, terminado) values(0, 'tour', 'Neiva', 'Traer bici', 3, 3, 0);
insert into TourEntity (id, nombre, lugar, descripcion, duracion, costo, terminado) values(1, 'tours', 'Neivas', 'Traer bicis', 4, 4, 0);

update BlogEntity set siguiente_id = 1 where id = 0;
update BlogEntity set anterior_id = 2 where id = 6;

update BlogEntity set tour_id = 0 where id = 1;
update BlogEntity set tour_id = 0 where id = 2;

update BlogEntity set creador_id = 300 where id = 1;

insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (0,'Seguro contra robo de bicicletas','El robo de debe reportar 24 horas despues de sucedido para hacer valido el seguro', 'Antirobo');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (1,'Seguro para fallecimiento','Se hace efectivo si es cliente fallece en un accidente durante el tour', 'Seguro de vida');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2,'Seguro para lesiones','Se cubren todos los gastos medicos que requiera el participante tras sufrir una lesion en el tour', 'Seguro de salud');

  
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (100, 'Juan González', 'code1', 'js.gonzalez15@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (200, 'Diego Rueda', 'code2', 'jd.ruedaa@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (300, 'Oscar Castañeda', 'code3', 'oj.castaneda@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (400, 'Michel Succar', 'code4', 'm.succar@uniandes.edu.co', 1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (500, 'Maria Clara', 'code5', 'mc.noguera@uniandes.edu.co', 1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (600, 'Jhuliana Barrios', 'code6', 'jj.barriosm@uniandes.edu.co',1,1,10);

insert into FotoEntity (id, ruta, tour_id) values (300, 'ruta1', 0);
insert into FotoEntity (id, ruta, tour_id) values (400, 'ruta2', 0);
insert into FotoEntity (id, ruta, tour_id) values (500, 'ruta3', 0);
insert into FotoEntity (id, ruta, tour_id) values (600, 'ruta4', 0);
insert into FotoEntity (id, ruta, tour_id) values (700, 'ruta5', 1);
insert into FotoEntity (id, ruta, tour_id) values (800, 'ruta6', 1);

