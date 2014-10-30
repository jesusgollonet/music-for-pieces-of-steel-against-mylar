s.boot
(
"synthdefs.sc".loadRelative;
"utils.sc".loadRelative;
)

(

~env = ~env ? ();

~env.count = 0;
~env.seedPattern =[1,1,1,0,1,1,0,1,0,1,1,0];
~env.justNotes = all{:y, y<-(0..(~env.seedPattern.size)),~env.seedPattern[y]== 1}.scramble;
~env.currentAccumulatedPattern = 0 ! ~env.seedPattern.size;
~env.currentAccumulatedPattern.postln;
Penvir(~env,
	Pbind(
		\instrument, \pbf,
		\dur, 0.15,
		\bufnum, Pseq([b.at(\clamp)],inf),
		\pan, Pseq([-0.5,0.5],inf),
		\amp, Pn(Plazy({
			var currentIndex = ~justNotes[~count];
			~currentAccumulatedPattern[currentIndex] = ~seedPattern[currentIndex];
			~currentAccumulatedPattern.postln;
			~count = ~count + 1;
			~count = ~count % ~justNotes.size;
			Pn(Pseq(~currentAccumulatedPattern,1),4);
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