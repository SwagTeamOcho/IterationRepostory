/*
 * @author
 * Alex Ruggiero
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class FindProf extends JPanel implements ActionListener {

	static JFrame frame;
	static JComboBox patternList;
	JLabel result;
	String currentProf;

	//http://www.wpi.edu/academics/facultydir/facultyname.html
	//http://wpi.collegeacronyms.org/buildings.html
	String[] profName = {
			"Select a Professor",
			//A's  =======================================
			"Abdullayev, Farhod ",
			"Abraham, Jon",
			// "Adams, David 'Dave' S.",
			"Addison, W. A. Bland, Jr.",
			"Agloro, Alexandrina",
			"Agu, Emmanuel O.",
			//"Albano, Leonard D.",
			//"Albrecht, Dirk R.",
			//"Ambay, Sakthikumar",
			"Apelian, Diran",
			//"Aravind, Padmanabhan K.",
			//"Argüello, José M.",
			"Arroyo, Ivron",
			"Ault, Holly K.",
			//B's  =====================================
			//"Bakermans, Marja",
			"Baller, William A.",
			"Bar-On, Isa",
			"Barton, Scott",
			"Beck, Joseph",
			"Belz, Melissa Malouf",
			//"Berenson, Dmitry",
			//"Bergendahl, John A.",
			"Bianchi, Frederick W.",
			"Bichuch, Maxim",
			"Bigonah, Roshanak",
			//"Billiar, Kristen L.",
			"Bitar, Stephen J.",
			"Blais, Marcel Y.",
			"Blandino, John Joseph",
			"Bogdanov, Gene",
			"Boudreau, Kristin",
			"Brattin, Joel J.",
			"Brown, David C.",
			"Brown, Christopher A.",
			"Brown, Donald R. III",
			//"Buckholt, Mike",
			"Bulled, Nicola L.",
			"Bullock, Steven C.",
			//"Burdette, Shawn C.",
			//"Burnham, Nancy A.",
			//C's  ======================================
			//"Camesano, Terri A.",
			"Capogna, Luca",
			"Carrera, Fabio",
			"Chernova, Sonia",
			"Christopher, Peter R.",
			"Ciaraldi, Michael J.",
			"Clancy, Edward 'Ted' A.",
			//"Clark, William M.",
			"Clark, Andrew",
			"Clark, Constance",
			"Claypool, Mark",
			"Cocola, Jim",
			"Cowlagi, Raghvendra V.",
			"Cullon, Joseph F.",
			"Cyganski, David",
			"Cyr, Martha",
			//D's  ======================================
			"Daniello, Robert",
			//"Datta, Ravindra",
			"Davis, Paul W.",
			"Dehner, Corey Denenberg",
			"Delorey, John F.",
			//"Delvy, Paul",
			//"Dembsey, Nicholas A.",
			"Demetriou, Michael A. ",
			"Demetry, Chrysanthe",
			//"Dempski, Robert E.",
			//"DeRosa, Lt Col Michael L.",
			//"Deskins, Nathaniel A.",
			"deWinter, Jennifer",
			//"DiBiasio, David",
			"Dimentberg, Mikhail F.",
			//"Dittami, James P.",
			//"Dixon, Anthony G.",
			"Djamasbi, Soussan",
			//"Dominko, Tanja",
			"Doremus, Scott",
			"Dougherty, Daniel J.",
			"Doyle, James Kevin",
			"Duckworth, R. James",
			//"Duffy, Joseph "Duff" B.",
			//E's  ======================================			
			//"Eberbach, Eugene",
			"Eddy, Beth",
			"Eisenbarth, Thomas",
			"Elgert, Laureen",
			//"El-Korchi, Tahar",
			"Elmes, Michael B.",
			"Eltabakh, Mohamed",
			"Emanuel, Alexander E.",
			"Emdad, Fatemeh",
			//"Emmert, Marion H.",
			"Ephraim, Michelle",
			//F's  ======================================			
			"Faber, Brenton",
			"Farbrook, Joseph",
			//"Farny, Natalie G.",
			"Farr, William W.",
			"Fehribach, Joseph D.",
			"Finkel, David",
			//"Fischer, Gregory S.",
			"Fisler, Kathi",
			"Fofana, Mustapha S.",
			"Forgeng, Jeffrey",
			//"French, Brent E.",
			"Furlong, Cosme",
			//G's  ======================================			
			"Gatsonis, Nikolaos A.",
			//"Gaudette, Glenn R.",
			//"Gegear, Robert J.",
			//"Gennert, Michael A.",
			//"Gericke, Arne",
			//"Gerstenfeld, Arthur 'Art'",
			//"Ginzberg, Michael J.",
			"Golding, Dominic",
			"Gonsalves, Edward M.",
			"Gottlieb, Roger S.",
			"Goulet, John A.",
			//"Gram, Richard C.",
			//"Grebinar, Philip",
			//"Grimm, Ronald",
			"Guttman, Joshua D.",
			//H's  ======================================			
			"Hakim, Hossein",
			"Hall-Phillips, Adrienne",
			"Hamel, Glynis M.",
			"Han, Qi",
			"Hanlan, James P.",
			"Hanlan, Erika A. S.",
			"Hansen, Peter H.",
			"Harrison, Lane Taylor",
			//"Hart, Frederick L.",
			//"Hebert-Maccaro, Karen A.",
			//"Hedly, John W.",
			"Heffernan, Neil T.",
			//"Hegedus, Roy D.",
			"Heineman, George T.",
			"Heinricher, Arthur C.",
			"Hempel, Maria",
			"Hersh, Robert",
			"Higgins, Lorraine D.",
			"Higgins, Huong N.",
			"Hofri, Micha",
			"Hou, Zhikun",
			//"Hoy, Frank",
			"Huang, Xinming",
			"Humi, Mayer",
			//I's  ======================================			
			//"Iannacchione, Germano S.",
			"Im, Seong-kyun",
			//J's  ======================================			
			//"Jain, Anjana",
			"Jarvis, Susan M.",
			"Jiusto, Scott",
			"John, Melissa-Sue",
			"Johnson, Michael Richard",
			"Johnson, Sharon A.",
			//K's  ======================================
			//"Kaminski, George A.",
			"Karanjgaokar, Nikhil",
			//"Kasouf, Chickery 'Chick' J.",
			//Nikolaos Kazantzis
			"Keller, Marie Tanya",
			//"Kim, Yeesock",
			"Kinicki, Bob",
			//"Kmiotek, Stephen James",
			"Kong, Xiangnan",
			"Konrad, Renata",
			"Korkin, Dmitry",
			//"Koutmos, Dimitrios",
			//"Krein, William 'Bill' A.",
			"Krueger, Robert",
			//"Kumar, Uma T.",
			//L's  ======================================
			"Lados, Diana A.",
			"Lai, Lifeng",
			"Landau, Susan",
			"Larsen, Christopher",
			"Lauer, Hugh C.",
			//"Lee, Kwonmoo",
			//"LePage, Suzanne",
			"Levey, Fiona C.",
			"Li, Yanhua",
			"Liang, Jianyu",
			"Lindeman, Jacob C",
			"Lindeman, Robert W.",
			//"Lindholm, Jed",
			"Liu, Yuxiang Shawn",
			"Ljungquist, Kent P.",
			"Loiacono, Eleanor T.",
			//"Lombardi, Robert P.",
			"Looft, Fred J.",
			"Ludwing, Reinhold",
			"Lui, Roger",
			"Lurie, Konstantin A.",
			//M's  ======================================
			//"Ma, Yi (Ed) Hua",
			//"MacDonald, John C.",
			"Madan, Aarti S.",
			"Makarov, Sergey N.",
			"Makhlouf, Makhlouf M.",
			//"Mallick, Rajib Basu",
			"Malone, J. J.",
			//"Manning, Amity",
			"Manzon, V. J.",
			"Martin, William J.",
			"Massoud, Yehia",
			//"Mathews, Lauren",
			//"Mathisen, Paul",
			"McCauley, Stephen",
			"McKenna, James P.",
			"McNeill, John A.",
			"McWeeny, Jennifer",
			//"Meacham, Brian J.",
			//"Medich, David Christopher",
			"Mello-Stark, Suzanne I.",
			//"Mendelson, Yitzhak",
			//"Mendoza-Abarca, Karla",
			//"Michalson, William R.",
			"Miller, Fabienne",
			"Mishra, Brajendra",
			"Monat, Jamie P.",
			"Moriarty, Brian",
			"Morton, Maxwell R.",
			"Mosco, Umberto",
			"Mott, Wesley T.",
			//N's  ======================================
			"Nandram, Balgobin",
			"Navabi, Zainalabedin",
			//"Notarianni, Kathy A.",
			//O's  ======================================
			"O’Brien, Kymberlee M.",
			"Oates, Karen Kashmanian",
			"Olinger, David J.",
			"Olson, Sarah D.",
			"Onal, Cagdas",
			"Orr, John A.",
			"Ottmar, Erin R.",
			//"Overstrom, Eric W.",
			//P's  ======================================
			"Paffenroth, Randy",
			//"Page, Raymond L.",
			"Pahlavan, Kaveh",
			"Panchapakesan, Balaji",
			"Park, Hyungbin",
			"Pavlov, Oleg V.",
			"Pedersen, Peder C.",
			"Peet, Creighton R.",
			"Peiris, Buddika",
			//"Peterson, Amy M.",
			"Petruccelli, Joseph D.",
			//"Pietroforte, Roberto",
			//"Pins, George D.",
			//"Plummer, Jeanine D.",
			//Politz, Samuel 'Sam' M.",
			"Pollice, Gary F.",
			//"Popovic, Marko B.",
			"Posterro, Barry",
			//"Pray, Keith A.",
			//"Rao, Reeta Prusty",
			//"Puchovsky, Milosh T.",
			//"Putnam, Craig",
			//Q's  ======================================
			//"Quimby, Richard S.",
			//R's  ======================================
			//"Radzicki, Michael",
			//"Rahbar, Nima",
			//"Ram-Mohan, L. Ramdas",
			//"Rangwala, Ali S.",
			"Rao, Pratap Mahesh",
			//"Rashid, Kamal A.",
			//"Reidinger, Amanda Zoe",
			//"Rice, Mark P.",
			"Rich, Charles",
			"Richman, Mark W.",
			"Rissmiller, Kent J.",
			"Rivera, Angel A.",
			//"Roberts, Susan C.",
			//"Roberts, Louis Anthony",
			"Robertson, Tom",
			//"Rolle, Marsha",
			"Rong, Yiming 'Kevin'",
			"Rosenstock, Josh Pablo",
			"Rostami, Minghao W.",
			"Rudolph, Jennifer M.",
			"Ruiz, Carolina",
			//"Rulfs, Jill",
			"Rundensteiner, Elke Angelika",
			"Runstrom, Scott P.",
			//"Ryder, Elizabeth 'Liz' F.",
			//S's  ======================================
			//"Saeed, Khalid",
			//"Sakulich, Aaron Richard",
			//"Salazar, Guillermo F.",
			"Samson, M. David",
			"Sanbonmatsu, John",
			//"Sarkis, Joseph",
			"Sarkis, Marcus",
			"Sarkozy, Gabor",
			"Savilonis, Brian J.",
			//"Scarlata, Suzanne Frances",
			"Schachterle, Lance",
			"Schaufeld, Jerome 'Jerry' J.",
			"Servatius, Brigitte",
			//"Shah, Purvi",
			"Sheldon, Lee",
			//"Shell, Scarlet",
			"Shim, Eunmi",
			"Shivkumar, Satya",
			"Shockey, Ingrid K.",
			"Shue, Craig A.",
			"Sidner, Candace L.",
			"Sisson, Richard D.",
			"Skorinko, Jeanine",
			"Smith, Alexander",
			"Smith, Ruth Lynette",
			"Snyder, Britton Robert",
			"Spanagel, David I.",
			//"Srinivasan, Jagan",
			"Stabile, Joe",
			"Stafford, Kenneth Alan",
			"Stapleton, Patricia A.",
			//"Stefano, Ciro C.",
			//"Stitt, William C.",
			"Strong, Diane M.",
			"Sturm, Stephan",
			"Sullivan, John M.",
			"Sunar, Berk",
			"Sutter, Ralph Paul Hendrik",
			//"Sweeney, Kevin M.",
			//T's  ======================================
			"Tang, Dalin",
			//"Tao, Mingjiang"
			"Taylor, Steven S.",
			//"Thompson, Robert W.",
			"Tilley, Burt S.",
			//"Timko, Michael T.",
			//"Torres-Jara, Eduardo Rafael",
			"Towner, Walter 'Wally'",
			"Trapp, Andrew C.",
			//"Troy, Karen Lindsay",
			"Troy, William 'Bill' F.",
			"Tuler, Seth P.",
			"Tulu, Bengisu",
			//V's  ======================================
			//"Van Dessel, Steven",
			"Vassallo, Helen G.",
			//"Vaz, Richard F.",
			"Venkatasubramanian, Krishna Kumar",
			//"Vermes, Domokos",
			"Vernescu, Bogdan M.",
			"Vernon-Gerstenfeld, Susan",
			"Vick, Susan",
			//"Vidali, Luis",
			"Virani, Shamsnaz",
			"Volkov, Darko",
			//W's  ======================================
			"Walker, Homer F.",
			"Wang, Yan",
			"Wang, Gu",
			"Wang, Justin",
			//"Weathers, Pamela "Pam" J.",
			"Weekes, Suzanne L.",
			"Weeks, Douglas G.",
			//"Wen, Qi",
			//"Wermann, Kenneth",
			//"Whitefleet-Smith, JoAnn L.",
			"Wills, Craig",
			"Wilson, E. Vance",
			"Wilson, Gerard 'Gerry' ",
			"Wobbe, Kristin K.",
			"Wodin-Schwartz, Sarah",
			"Wong, Wilson",
			"Worthington, Shari L.",
			"Wu, Zheyang",
			"Wulf, Sharon A.",
			"Wyglinski, Alexander",
			//Y's  ======================================
			"Yagoobi, Jamal S.",
			"Yakovlev, Vadim V.",
			//Z's  ======================================
			"Zeng, Amy Z.",
			"Zhang, Zhongqiang",
			//"Zhou, Hong Susan",
			"Zhu, Joe",
			//"Zizza, Keith",
			"Zou, Jian",	
	};
	String[] profRoom = {
			null,
			//A's  ======================================
			"SL405B",
			"SH002",
			//Life Sciences & Bioengineering Center (Gateway), 4003
			"SL238",
			"SL208",
			"FL139",
			//"KH201",
			//Life Sciences & Bioengineering Center (Gateway), 3017
			//"GH128B",
			"WB326",
			//"OH212B",
			//"Life Sciences & Bioengineering Center (Gateway), 4021
			"SL317A",
			"HL208",
			//B's  =====================================
			//"GH005",
			"SL408C",
			"WB224",
			"AH 208", //Alden Hall
			"FL138",
			"PC210",
			//85 Prescott, 212
			//"KH208",
			"AH205",
			"SH305D",
			"SL031",
			//Life Sciences & Bioengineering Center (Gateway), 4007
			"AK312",
			"SH104A",
			"HL239",
			"AK020",
			"SL125",
			"SL024",
			"FL131",
			"WB235",
			"AK313",
			//"GH203A",
			"PC214",
			"SL235",
			//Life Sciences & Bioengineering Center (Gateway), 3023
			//"OH219",
			//C's  ======================================		
			//Life Sciences & Bioengineering Center (Gateway), 4002
			"SH108",
			"SL113",
			"Ak125",
			"SH305B",
			"FL129",
			"AK304",
			//"GH124",
			"AK216",
			"SL408S",
			"FLB24A",
			"SL026",
			"HL247",
			"SL241",
			"AK128",
			"SL233",
			//D's  ======================================
			"HL150",
			//"GH014A",
			"SL405F",
			"PC210",
			"AH205",
			//null, //not lsited
			//Gateway Park, II 1212
			"HL243",
			"GL302",
			//Life Sciences & Bioengineering Center (Gateway), 3005
			//37 Institute Road (Air Force ROTC)
			//"GH014B",
			"SL015",
			//"GH122",
			"HL212",
			//Life Sciences & Bioengineering Center (Gateway), 3021
			//"GH224A",
			"WB216",
			//Life Sciences & Bioengineering Center (Gateway), 3004
			"WB215",
			"FL136",
			"SL311",
			"AK301",
			//"Life Sciences & Bioengineering Center (Gateway), GP4015
			//E's  ======================================
			//85 Prescott, 213
			"SL223E",
			"AK307",
			"SL 310E",
			//"KH101",
			"WB203",
			"FL235",
			"AK215",
			"AK129",
			//Life Sciences & Bioengineering Center (Gateway), 3027
			"SL237",
			//F's  ======================================			
			"SL019",
			"SL207",
			//"GH203C",
			"SH105A",
			"SH201C",
			"FLB23",
			//85 Prescott, 234
			"FL130",
			"WB311B",
			"SL008",
			//"WB",//No room listed
			"HL151",
			//G's  ======================================			
			"HL244",
			//Life Sciences & Bioengineering Center (Gateway), 4012
			//Life Sciences & Bioengineering Center (Gateway), 4016
			//85 Prescott, 203
			//Life Sciences & Bioengineering Center (Gateway)
			//50 Prescott, 1317
			//Gateway Park, 1313
			"PC212",
			"WB217",
			"SL004",
			"SH201A",
			//"WB"//No room listed
			//"AL",//Alumni Gym; no room listed
			//Life Sciences & Bioengineering Center (Gateway), 3027
			"FL137",
			//H's  ======================================			
			"AK231",
			"WB210",
			"FL132",
			"SL405D",
			"SL028",
			"SL018",
			"SL107",
			"FL136",
			//"KH206",
			//50 Prescott, 1311
			//"WB"//No room list
			"FL237",
			//Life Sciences & Bioengineering Center, 3014
			"FLB20",
			"BH108",
			"SL405C",
			"PC211",
			"SL020",
			"WB306",
			"FL133",
			"HL209",
			//50 Prescott, 1319
			"AK303",
			"SH303",
			//I's  ======================================			
			//"OH120",
			"HL240",
			//J's  ======================================			
			//"Life Sciences & Bioengineering Center (Gateway), 4004
			"AK011",
			"PC205",
			"SL317B",
			"SH201B",
			"WB223",
			//K's  ======================================
			//Life Sciences & Bioengineering Center (Gateway), 4019
			"HL242",
			//50 Prescott, 1318
			//"GH121",
			"SL031",
			//"KH209D",
			"FL135",
			//"GH120",
			"AK125",
			"WB205",
			"FLB22",
			//"WB"//No room listed
			//"WB"//No room listed
			"SL223C",
			//"GH103C",
			//L's  ======================================
			"HL207",
			"AK204",
			"SL310F",
			"SH109B",
			"FL144",
			//Life Sciences & Bioengineering Center (Gateway), 4036
			//"KH209A",
			"HL104",
			"AK130",
			"WB311A",
			"FL243",
			"FLB24B",
			//Not listed
			"HL110",
			"SL223D",
			"WB209",
			//"WB", //Room not listed
			"AK012",
			"AK228",
			"SH105B",
			"SH010",
			//M's  ======================================
			//"GH224B",
			//Life Sciences & Bioengineering Center (Gateway), 3022
			"SL003",
			"AK306",
			"WB334",
			//"KH115",
			"SL405E",
			//Life Sciences & Bioengineering Center (Gateway), 4025
			"AH209",
			"SH305A",
			"AK203",
			//Life Sciences & Bioengineering Center, 4006
			//"KH209E",
			"PC212",
			"WB216C",
			"AK305",
			"SL330",
			//Gateway Park, II 1213
			//"OH127",
			"FL021B",
			//Gateway Park, 4008
			//50 Prescott, 1331
			//85 Prescott, 204
			"WB302",
			"WB331",
			"WB215",
			"SL205",
			"WB215",
			"SH307",
			"SL236",
			//N's  ======================================
			"SH002",
			"AK319",
			//Gateway Park, II 1211
			//O's  ======================================
			"SL334",
			"SL121",
			"HL242",
			"SH302",
			"HL105",
			"AK214",
			"SL317C",
			//Gateway Park, GP3002
			//P's  ======================================
			"AK124",
			//Life Sciences & Bioengineering Center (Gateway), 4005
			"AK308",
			"AK210",
			"SL405F",
			"SL310A",
			"AK205",
			"SL331",
			"SH101",
			//Life Sciences & Bioengineering Center, Gateway Park I: GP4002
			"SH103",
			//"KH209B",
			//Life Sciences & Bioengineering Center (Gateway), 4010
			//"KH106",
			//Life Sciences & Bioengineering Center (Gateway), 4023
			"FLB19",
			//"OH215",
			"SH105D",
			//"FL"//No room listed
			//Life Sciences & Bioengineering Center (Gateway), 4013
			//Gateway Park, II 1209
			//85 Prescott, 208
			//Q's  ======================================
			//"OH128",
			//R's  ======================================
			//Not listed
			//"KH104",
			//"OH224",
			//Gateway Park, II 1210
			"HL106",
			//50 Prescott, 2045
			//"GH303A",
			//50 Prescott, 1332
			"FLB25B",
			"HL245",
			"SL315",
			"SL016",
			//"GH125",
			//"GH203D",
			"SL234",
			//Life Sciences & Bioengineering Center (Gateway), 4011
			"WB307",
			"SL208",
			"SL405C",
			"SL408B",
			"FL232",
			//"GH128A",
			"FL238",
			"SL106",
			//Life Sciences & Bioengineering Center (Gateway), 4024
			//S's  ======================================
			//"SL"//No room listed
			//"KH209C",
			//"KH202A",
			"SL014",
			"SL023",
			//50 Prescott, Room 1316
			"SH206",
			"FL141",
			"HL108",
			//Life Sciences & Bioengineering Center (Gateway), 3003
			"SL027",
			"WB103",
			"SH305C",
			//Life Sciences & Bioengineering Center, Gateway II Room 1315
			"SL211",
			//Life Sciences & Bioengineering Center (Gateway), 4034
			"AH211",
			"WB227",
			"PC209",
			"FL236",
			"FLB21",
			"WB246B",
			"SL317C",
			"SL310B",
			"SL108",
			"SL210",
			"SL239",
			//Life Sciences & Bioengineering Center (Gateway), 4026
			"HL153",
			"HL011",
			"SL223B",
			//Not listed
			//50 Prescott, 1314
			"WB219",
			"SH202C",
			"HL111",
			"AK302",
			"SL206",
			//50 Prescott, Suite #1324
			//T's  ======================================
			"SH202E",
			//"KH107",
			"WB101",
			//"GH119",
			"SH202A",
			//"GH123",
			//85 Prescott, 124
			"WB222",
			"WB207",
			//Life Sciences & Bioengineering Center (Gateway), 4033
			"WB216C",
			"PC211",
			"WB201",
			//V's  ======================================
			//"KH117A",
			"WB202",
			//"PC",//No room listed
			"FL137",
			//"SH", //No room listed
			"SH002",
			"SL331",
			"SL017",
			//Life Sciences & Bioengineering Center (Gateway), 4018
			"AK010",
			"SH104B",
			//W's  ======================================
			"SH009A",
			"WB238",
			"SH305D",
			"WB303",
			//Life Sciences & Bioengineering Center (Gateway), 4022
			"SH109B",
			"AH212",
			//"OH213A",
			//"WB",//No room listed
			//"GH203B",
			"FL234",
			"WB102",
			"WB216C",
			"CC233",
			"HL206",
			"FLB19",
			"WB216C",
			"SH101",
			"WB215",
			"AK230",
			//Y's  ======================================
			"HL130",	
			"SH104C",
			//Z's  ======================================
			"WB308",
			"SH302A",
			//Life Sciences & Bioengineering Center (Gateway), 4001
			"WB301",
			//"FL",//No room listed
			"SH102",
	};
	public FindProf() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Set up the UI for selecting a pattern.
		JLabel patternLabel1 = new JLabel("Enter the name of the professor");
		JLabel patternLabel2 = new JLabel("");

		patternList = new JComboBox(profName);
		patternList.setEditable(true);
		patternList.setMaximumRowCount(5);
		patternList.addActionListener(this);

		//Create the UI for displaying result.
		JLabel resultLabel = new JLabel("Professor's Office Number:",
				JLabel.LEADING); //== LEFT
		result = new JLabel(" ");
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setForeground(Color.black);
		result.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black),
				BorderFactory.createEmptyBorder(5,5,5,5)
				));

		//Lay out everything.
		JPanel patternPanel = new JPanel();
		patternPanel.setLayout(new BoxLayout(patternPanel,
				BoxLayout.PAGE_AXIS));
		patternPanel.add(patternLabel1);
		patternPanel.add(patternLabel2);
		patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
		patternPanel.add(patternList);

		JPanel resultPanel = new JPanel(new GridLayout(0, 1));
		resultPanel.add(resultLabel);
		resultPanel.add(result);

		patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(patternPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(resultPanel);

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	} //constructor

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String newSelection = (String)cb.getSelectedItem();
		currentProf = newSelection;
		findRoom();
	}

	public void findRoom() {
		for (int i = 0; i < profName.length; i++){
			if(currentProf == profName[i]){
				result.setText(profRoom[i]);
				break;
			}
		}

	}

	/**
	 * Create the GUI and show it.  
	 */
	public static void createFindProf() {
		//Create and set up the window.
		JFrame frame = new JFrame("Find Profesor");
		frame.setBounds(600, 300, 80, 40);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create and set up the content pane.
		JComponent newContentPane = new FindProf();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
		frame.setResizable(false);

		AutoCompletion.enable(patternList);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
/*
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createFindProf();
			}
		});
	}
	*/
}
