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
		\bufnum, Pseq([b.at(\clamp)],inf),
		\pan, Pseq([-0.5,0.5],inf),
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
			Pn(Pseq(ar,1),4);
		}) * 0.1,inf);
	)
).play(quant:1);


f = [1,1,1,0,1,1,0,1,0,1,1,0];
g = f.rotate(1);
h = f.rotate(2);

// all patterns 'inherit' from this one
~basePbind = Pbind(
    \instrument, \pbf,
    \dur, 0.15
);

p = Pbindf(
    ~basePbind,
	\bufnum, Pseq([b.at(\k1r), b.at(\k1p)],inf),
    \pan, 0,
    \amp, Pseq(f,inf) * 0.1
).play(quant:1);


)