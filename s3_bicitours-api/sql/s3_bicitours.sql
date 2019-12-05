delete from ComentarioEntity;
delete from BlogEntity;
delete from BlogEntity_UsuarioEntity;
delete from UsuarioEntity; 
delete from SeguroEntity;
delete from FotoEntity;
delete from TourEntity;
delete from ComentarioEntity_ComentarioEntity;
delete from EventoEntity;
delete from RecomendacionEntity;

/**NOTACIÓN PARA IDs: Usuarios son 100X, Seguros son 200X, Tours son 300X, Blogs son 400X, Comentarios son 500X**/

/** Caso de usuario administrador con varios Tours adquiridos**/  
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1000, 'Juan González', 'code1', 'js.gonzalez15@uniandes.edu.co',1,1,0);
    /**Tours asociados a Usuario id 1000**/
    insert into TourEntity (id, nombre, lugar, descripcion, dificultad, duracion, costo, terminado) 
    values (3000,'El tour de las estrellas', 'Tunja','El Tour más antiguo de GoBici, una leyenda de leyendas apta solo para los más expertos',ALTA,235,500000,0);
    insert into TourEntity (id, nombre, lugar, descripcion, dificultad, duracion, costo, terminado)
    values (3001,'El tour escarabajo', 'Pasto','El Tour ascendente más largo de GoBici, solo para quienes pedalean sin parar por horas',ALTA,300,400000,0);
    insert into TourEntity (id, nombre, lugar, descripcion, dificultad, duracion, costo, terminado)
    values (3002,'El tour oruga', 'Pasto','El Tour para quienes empiezan una nueva vida',BAJA,100,250000,0);

/** Caso de usuario con varios comentario que le pertenecen **/
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1001, 'Diego Rueda', 'code2', 'jd.ruedaa@uniandes.edu.co',1,0,0);
    /** Comentarios asociados usuario id 1001**/
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5011, 'Esto me alegro el día', 4, 1001, 1001, 4000);
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5012, 'Nice', 4, 1001, 1001, 4000);
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5013, 'Best moment ever', 4, 1001, 1001, 4001);
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5014, 'Casi lloro con esa obra', 4, 1001, 1001, 4001);
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5015, 'Quien lo diría', 4, 1001, 1001, 4002);
    insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5016, 'Nice', 4, 1001, 1001, 4002);

/** Caso de usuario administrador con varios Blogs y comentarios que le pertenecen **/
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1002, 'Oscar Castañeda', 'code3', 'oj.castaneda@uniandes.edu.co',1,1,0);
    /**Blogs asociados a Usuario id 1002**/
    insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaImagen, rutaVideo, creador_id) 
    values (4000, 'My name is Yoshikage Kira',4,'My name is Yoshikage Kira. I''m 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest...','https://i.kym-cdn.com/entries/icons/original/000/029/929/Kira_decides_to_tail_Shigechi.png',
    '',1002);
        /**Comentarios asociados a blog id 4000**/
        insert into ComentarioEntity (id, texto, calificacion, usuario_id, blog_id) values (5000, 'El mejor monólogo de todos', 1, 1002, 4000);
        insert into ComentarioEntity (id, texto, calificacion, usuario_id, blog_id) values (5002, 'Killer Queen Bites The Dust', 3, 1000, 4000);
        insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5001, 'Nada mal', 4, 1000, 1002, 4000);
        
    insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaVideo, creador_id ) 
    values (4001,'The tragedy of Darth Plagueis The Wise',4,'Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It''s not a story the Jedi would tell you. It''s a Sith legend...','https://www.youtube.com/embed/05dT34hGRdg'
    ,1002 );
        /**Comentarios asociados a blog id 4001**/
        insert into ComentarioEntity (id, texto, calificacion, usuario_id, blog_id) values (5000, 'Anakin debió darse cuenta en ese momento', 1, 1002, 4001);
        insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5001, 'Best meme ever', 4, 1001, 1002, 4001);

    insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaImagen, rutaVideo, creador_id) 
    values (4002,'Azer isn''t so great?',4,'Azer isn''t so great? Are you kidding me? When was the last time you saw a player with such aim ability and movement with a tablet?','https://upload.wikimedia.org/wikipedia/commons/d/d3/Osu%21Logo_%282015%29.png',
    '',1002);
        /**Comentarios asociados a blog id 4002**/
        insert into ComentarioEntity (id, texto, calificacion, usuario_id, blog_id) values (5000, 'You almost got me there', 1, 1000, 4002);
        insert into ComentarioEntity (id, texto, calificacion, tour_id, usuario_id, blog_id) values (5001, 'por favor', 4, 1003, 1003, 4002);

/** Caso de usuario con un Tour adquirido y varios eventos en el tour**/
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1003, 'Michel Succar', 'code4', 'm.succar@uniandes.edu.co', 0,0,100);

/** Usuario sin ningún elemento asociado**/
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1004, 'Maria Clara', 'code5', 'mc.noguera@uniandes.edu.co', 0,0,10);

/** Caso de usuario administrador con varios Tours adquiridos y varios eventos en estos Tours**/
insert into UsuarioEntity (id, nombre, password, correo, pago, esAdmin, deuda) values (1005, 'Jhuliana Barrios', 'code6', 'jj.barriosm@uniandes.edu.co',0,1,1000);

/*****************************************************************************************************************************************************************/




insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2000,'Seguro contra robo de bicicletas','El robo de debe reportar 24 horas despues de sucedido para hacer valido el seguro', 'Antirobo');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2001,'Seguro para fallecimiento','Se hace efectivo si es cliente fallece en un accidente durante el tour', 'Seguro de vida');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2002,'Seguro para lesiones','Se cubren todos los gastos medicos que requiera el participante tras sufrir una lesion en el tour', 'Seguro de salud');



insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (0,'Seguro contra robo de bicicletas','El robo de debe reportar 24 horas despues de sucedido para hacer valido el seguro', 'Antirobo');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (1,'Seguro para fallecimiento','Se hace efectivo si es cliente fallece en un accidente durante el tour', 'Seguro de vida');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2,'Seguro para lesiones','Se cubren todos los gastos medicos que requiera el participante tras sufrir una lesion en el tour', 'Seguro de salud');

insert into TourEntity (id) values (1);


insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaVideo, creador_id) 
values (1003,
'BiciGo',
4,
'Felicidad y salud al alcance de tu bici',
'https://www.youtube.com/watch?v=QzqZiHwLVCY', 1002);

