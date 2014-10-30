s.boot
(
SynthDef("pbf", { |out = 0, bufnum = 0, rate = 1, amp = 0.5, pan = 0|
	var player, ratio, panned;
	ratio = BufRateScale.kr(bufnum) * rate;
	player = PlayBuf.ar(1, bufnum, ratio, doneAction:2);
	panned =  Pan2.ar(player , pan,  level:amp);
    Out.ar(0, panned);
}).add;
)
(
b = JGBUtil.dirToBuffers("/Users/jgb/Documents/projects/music-for-pieces-of-steel-against-mylar/dev/build/samples/",s);
)
(

~env = ~env? ();

~env.count = 0;
~env.seedPattern =[1,1,1,0,1,1,0,1,0,1,1,0];
~env.justNotes = all{:y, y<-(0..(~env.seedPattern.size)),~env.seedPattern[y]== 1};

Penvir(~env,
	Pbind(
		\instrument, \pbf,
		\dur, 0.15,
		\bufnum, b.at(\clamp),
		\pan, -1,
		\amp, Pn(Plazy({
			var ar = ~seedPattern.collect({|item,i|
				if (i <= ~justNotes[~count],{
					~seedPattern[i];
				},{
					0
				});
			});
			ar.postln;
			~count = ~count + 1;
			~count = ~count % ~justNotes.size;
			Pseq(ar,1);
		}) * 0.1,inf);
	)
).play;


)