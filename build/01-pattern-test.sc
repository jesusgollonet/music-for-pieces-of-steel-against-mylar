
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
    \bufnum, b.at(\clamp),
    \pan, -1,
    \amp, Pseq(f,inf)
);

q = Pbindf(
    ~basePbind,
    \bufnum, b.at(\k1r),
    \pan, 0,
    \amp, Pseq(g,inf)
);

r = Pbindf(
    ~basePbind,
    \bufnum, b.at(\k1p),
    \pan, 1,
    \amp, Pseq(h,inf)
);


Ptpar([
    0.0, p,
    12*4 * 0.15, q,
    12 * 8 * 0.15,r
]).play;

)