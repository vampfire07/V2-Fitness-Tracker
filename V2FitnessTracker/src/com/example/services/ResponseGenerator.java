package com.example.services;

import java.util.Random;

public class ResponseGenerator {
	
	private static Random random = new Random();

	public static String generate(State state) {
		String response = "";
		int num = random.nextInt(3) + 1;
		switch(state) {
		case V:
			if(num == 1) response = "It's not a good idea to keep losing weight in your age. You need all the nutrition you can get while growing up.";
			else if(num == 2) response = "You are in the stage of life when you need proper nutrition. Eat healthy and work on gaining some weight!";
			else if(num == 3) response = "Not a good idea to lose weight. You are underweight and in need of proper nutrition. You might regret it later on.";
			break;
		case W:
			if(num == 1) response = "You should aim for gaining some more weight. Remember to do it the healthy way!";
			else if(num == 2) response = "Think of sleep as letting your body take a break, and when it wakes up in the morning you are a much healthier person.";
			else if(num == 3) response = "Make sure you get enough sleep every night. If you feel energized at 8 hours of sleep, then it could be the right one for you.";
			break;
		case X:
			if(num == 1) response = "Always tell mom or dad to cook healthy foods. Remind them when they start cooking fried food every day. That's bad!";
			else if(num == 2) response = "Get rid of junk foods or hide them somewhere never to be found again.";
			else if(num == 3) response = "Remember to be active. Go outside and play some sports!";
			break;
		case Y:
			if(num == 1) response = "Don't aim to lose weight! Instead eat well and exercise regularly.";
			else if(num == 2) response = "I highly recommend for you NOT to aim for losing weight. You need to stay healthy as you are still growing up.";
			else if(num == 3) response = "Think again! You should be aiming to maintain your weight, if not gain some more.";
			break;
		case Z:
			if(num == 1) response = "Eat well. Remember, you're growing up! You seem to be on the right track.";
			else if(num == 2) response = "Nuts are good energy boosters. Have you tried munching on a few before you start your day?";
			else if(num == 3) response = "If the weather's good today, go play some sports. If not, stay at home and do some house chores.";
			break;
		case AA:
			if(num == 1) response = "Ask mom or dad to take you to the soccer field. Go learn the sport, it's fun!";
			else if(num == 2) response = "Do you have a pet dog? Play some catch together. If not, play frisbee with your best friend.";
			else if(num == 3) response = "Eat your veggies! You may not like the taste, but just think that you'll be stronger after eating it.";
			break;
		case AB:
			if(num == 1) response = "I can help you out. First thing you do: throw away junk foods if you still have any.";
			else if(num == 2) response = "Ask your parents to help you in becoming healthier. They will be your best mentors at this age.";
			else if(num == 3) response = "Candies are really bad for you. Eat fruits instead, they taste better anyway.";
			break;
		case AC:
			if(num == 1) response = "I highly suggest that you aim to drop some weight, young man.";
			else if(num == 2) response = "Chips are very salty and are not good for you. Don't eat them anymore!";
			else if(num == 3) response = "Have a regular checkup at the doctor's. Ask mom or dad to take you there.";
			break;
		case AD:
			if(num == 1) response = "Gain weight?? That's not good. You should aim to lose weight.";
			else if(num == 2) response = "Your goal should be to lose weight. Be careful!";
			else if(num == 3) response = "Stop the bad habits and start playing games outside. If the weather's good, play some sports!";
			break;
		case AE:
			if(num == 1) response = "It's not a good idea to keep losing weight in your age. You need all the nutrition you can get while growing up.";
			else if(num == 2) response = "You are in the stage of life when you need proper nutrition. Eat healthy and work on gaining some weight!";
			else if(num == 3) response = "Not a good idea to lose weight. You are underweight and in need of proper nutrition. You might regret it later on.";
			break;
		case AF:
			if(num == 1) response = "You should aim for gaining some more weight. Remember to do it the healthy way!";
			else if(num == 2) response = "Think of sleep as letting your body take a break, and when it wakes up in the morning you are a much healthier person.";
			else if(num == 3) response = "Make sure you get enough sleep every night. If you feel energized at 8 hours of sleep, then it could be the right one for you.";
			break;
		case AG:
			if(num == 1) response = "Always tell mom or dad to cook healthy foods. Remind them when they start cooking fried food every day. That's bad!";
			else if(num == 2) response = "Get rid of junk foods or hide them somewhere never to be found again.";
			else if(num == 3) response = "Remember to be active. Go outside and play some sports!";
			break;
		case AH:
			if(num == 1) response = "I can help you out. First thing you do: throw away junk foods if you still have any.";
			else if(num == 2) response = "Ask your parents to help you in becoming healthier. They will be your best mentors at this age.";
			else if(num == 3) response = "Candies are really bad for you. Eat fruits instead, they taste better anyway.";
			break;
		case AI:
			if(num == 1) response = "I highly suggest that you aim to drop some weight, young man.";
			else if(num == 2) response = "Chips are very salty and are not good for you. Don't eat them anymore!";
			else if(num == 3) response = "Have a regular checkup at the doctor's. Ask mom or dad to take you there.";
			break;
		case AJ:
			if(num == 1) response = "Gain weight?? That's not good. You should aim to lose weight.";
			else if(num == 2) response = "Your goal should be to lose weight. Be careful!";
			else if(num == 3) response = "Stop the bad habits and start playing games outside. If the weather's good, play some sports!";
			break;
		case AK:
			if(num == 1) response = "I can help you out. First thing you do: throw away junk foods if you still have any.";
			else if(num == 2) response = "Ask your parents to help you in becoming healthier. They will be your best mentors at this age.";
			else if(num == 3) response = "Candies are really bad for you. Eat fruits instead, they taste better anyway.";
			break;
		case AL:
			if(num == 1) response = "I highly suggest that you aim to drop some weight, young man.";
			else if(num == 2) response = "Chips are very salty and are not good for you. Don't eat them anymore!";
			else if(num == 3) response = "Have a regular checkup at the doctor's. Ask mom or dad to take you there.";
			break;
		case AM:
			if(num == 1) response = "Gain weight?? That's not good. You should aim to lose weight.";
			else if(num == 2) response = "Your goal should be to lose weight. Be careful!";
			else if(num == 3) response = "Stop the bad habits and start playing games outside. If the weather's good, play some sports!";
			break;
		case AN:
			if(num == 1) response = "Create a meal plan if you haven't yet. Time to achieve the NORMAL BMI status!"; 
			else if(num == 2) response = "Add more calories to your daily food intake. Pick the right types of food, though.";
			else if(num == 3) response = "It's time for you to gain some weight, frankly.";
			break;
		case AO:
			if(num == 1) response = "Choose foods that are packed with vitamins, minerals, and nutrients. One example is yogurt.";
			else if(num == 2) response = "Chips are very salty and are not good for you. Don't eat them anymore!";
			else if(num == 3) response = "Have a regular checkup at the doctor's. Ask mom or dad to take you there.";
			break;
		case AP:
			if(num == 1) response = "I suggest taking in some carbohydrates, or carbs as most call it. Rice and potatoes are rich in carbs.";
			else if(num == 2) response = "Make sure you're getting enough sleep. Let your body rest and give it some time to replenish.";
			else if(num == 3) response = "Don't let anyone else judge how you look. Feel good and aim to be healthy!";
			break;
		case AQ:
			if(num == 1) response = "You are charting perilous waters. Being classified as underweight may not be good for you in the long run.";
			else if(num == 2) response = "Are you sure that you want to lose weight? You are already in the normal BMI range.";
			else if(num == 3) response = "If you want to lose weight, make sure you have a balanced diet set.";
			break;
		case AR:
			if(num == 1) response = "Yoga seems to be a big hit these days. It develops good flexibility and balance. Have you done a session before?";
			else if(num == 2) response = "Getting fit takes discipline. The first thing you have to train is your mind. Get in the right mindset!";
			else if(num == 3) response = "All the hard work leads to better confidence and trust in yourself.";
			break;
		case AS:
			if(num == 1) response = "Strength and size are two ideal characteristics to improve. It is best to plan accordingly on what your goals are.";
			else if(num == 2) response = "Gaining weight means either gaining muscle size or fat. You know the obvious, right? ";
			else if(num == 3) response = "Foods rich in complex carbohydrates are acknowledged by your body when it comes to gaining weight.";
			break;
		case AT:
			if(num == 1) response = "Eat protein-rich foods and value working out.";
			else if(num == 2) response = "Get off the couch and apply for a gym membership if you don't have one yet.";
			else if(num == 3) response = "The first step to losing weight is getting rid of bad habits. Junk foods and lack of exercises are examples.";
			break;
		case AU:
			if(num == 1) response = "For some reason, you want to maintain your weight. For the best result, lose it.";
			else if(num == 2) response = "I highly recommend you change your goal. Lose weight instead.";
			else if(num == 3) response = "If you can turn those fat into muscle, that would impress me.";
			break;
		case AV:
			if(num == 1) response = "Change your goals. Do not keep adding pounds to your body.";
			else if(num == 2) response = "Your body might not be able to handle your weight. I suggest taking the right action now - LOSE WEIGHT!";
			else if(num == 3) response = "Your goal is not good for you. Losing weight and not gaining should be your aim.";
			break;
		case AW:
			if(num == 1) response = "Aging takes its toll on your body. Make sure you continue to live healthy.";
			else if(num == 2) response = "At this point in your life, your muscles start to diminish. Losing any more weight is already risky.";
			else if(num == 3) response = "Gaining weight should still be your aim. It's going to be harder to gain, but it's the better path than to";
			break;
		case AX:
			if(num == 1) response = "Don't let the stressors in life ever bring your motivation down.";
			else if(num == 2) response = "Make time for your friends, family, and loved ones. But do not overlook having time for yourself.";
			else if(num == 3) response = "If you take good care of your body, it will take good care of you as you continue to age.";
			break;
		case AY:
			if(num == 1) response = "Having trouble sleeping? Get a firm, relaxing bed; drink a glass of water before sleeping; relax your mind. These should help.";
			else if(num == 2) response = "Get into the habit of eating healthy, and take those meal plans seriously. Create a plan if you haven't yet";
			else if(num == 3) response = "If there's anything keeping you from working out, it's your mindset.";
			break;
		case AZ:
			if(num == 1) response = "Out with the bad, in with the good. Excessive fat is bad.";
			else if(num == 2) response = "Calcium will probably be one of your closest friends. Take it to heart: he can salvage your bones and teeth.";
			else if(num == 3) response = "Potassium helps in regulating high blood pressure. Bananas are popular for its potassium content. And so are potatoes.";
			break;
		case BA:
			if(num == 1) response = "Always check your bathroom scale. Make sure it's still accurate and is still leading you to the right track.";
			else if(num == 2) response = "Have a workout partner or buddy. You two can keep each other motivated and help reach each other's goals";
			else if(num == 3) response = "If you ever crave for sweets, make sure you CONTROL yourself. Take only a small amount to satisfy that craving.";
			break;
		case BB:
			if(num == 1) response = "Your age is fighting against your metabolism. Find more ways to keep your metabolism up and running.";
			else if(num == 2) response = "Water is a foolproof way to clean your systems out. Your body, I mean.";
			else if(num == 3) response = "You should change the definition of dieting from now on - from reduced eating to \"eating smarter\".";
			break;
		case BC:
			if(num == 1) response = "If you need the motivation to lose weight, look up some community running events on the Internet.";
			else if(num == 2) response = "Have a pet dog who you can walk and/or jog with. Their company helps you keep going.";
			else if(num == 3) response = "Toughen up! You should not rely on any coach to tell you what to do all the time. Have your own motivation.";
			break;
		case BD:
			if(num == 1) response = "As much as you need nutrition, being overweight or obese isn't going to help you.";
			else if(num == 2) response = "Your weight can not only deteriorate your body, but it can also deteriorate your self-esteem and self-trust.";
			else if(num == 3) response = "Lose weight! I cannot emphasize that further. Aim for getting a normal BMI.";
			break;
		case BE:
			if(num == 1) response = "There's really no point in adding more weight to yourself.";
			else if(num == 2) response = "Change your goals to something more rational. Gaining weight isn't one of them.";
			else if(num == 3) response = "\"LOSE WEIGHT.\" There, I said it.";
			break;
		case BF:
			if(num == 1) response = "Unless you are fasting for a large cause, you can stay underweight. As a fitness coach, I still won't ever recommend it.";
			else if(num == 2) response = "Put some healthy weight on. Have the young fellers help you out in your diet and exercise.";
			else if(num == 3) response = "From this point on, every movements and actions can be a challenge. If you treated your body well in the previous years, it will serve you well.";
			break;
		case BG:
			if(num == 1) response = "There are still things you can do and enjoy while being physically active. Walking, to say the least.";
			else if(num == 2) response = "Spend quality time with the people you love by going to the park and having a picnic.";
			else if(num == 3) response = "Do some stretches every morning. Wake up early and enjoy the benefits of healthy sunlight.";
			break;
		case BH:
			if(num == 1) response = "Having trouble sleeping? Get a firm, relaxing bed; drink a glass of water before sleeping; relax your mind. These should help.";
			else if(num == 2) response = "Get into the habit of eating healthy, and take those meal plans seriously. Create a plan if you haven't yet";
			else if(num == 3) response = "If there's anything keeping you from working out, it's your mindset.";
			break;
		case BI:
			if(num == 1) response = "Out with the bad, in with the good. Excessive fat is bad.";
			else if(num == 2) response = "Calcium will probably be one of your closest friends. Take it to heart: he can salvage your bones and teeth.";
			else if(num == 3) response = "Potassium helps in regulating high blood pressure. Bananas are popular for its potassium content. And so are potatoes.";
			break;
		case BJ:
			if(num == 1) response = "Always check your bathroom scale. Make sure it's still accurate and is still leading you to the right track.";
			else if(num == 2) response = "Have a workout partner or buddy. You two can keep each other motivated and help reach each other's goals";
			else if(num == 3) response = "If you ever crave for sweets, make sure you CONTROL yourself. Take only a small amount to satisfy that craving.";
			break;
		case BK:
			if(num == 1) response = "Your age is fighting against your metabolism. Find more ways to keep your metabolism up and running.";
			else if(num == 2) response = "Water is a foolproof way to clean your systems out. Your body, I mean.";
			else if(num == 3) response = "You should change the definition of dieting from now on - from reduced eating to \"eating smarter\".";
			break;
		case BL:
			if(num == 1) response = "List down your favorite pasttimes. Incorporate physical activities on every single one of them.";
			else if(num == 2) response = "Have a pet dog who you can walk and/or jog with. Their company helps you keep going.";
			else if(num == 3) response = "Toughen up! You should not rely on any coach to tell you what to do all the time. Have your own motivation.";
			break;
		case BM:
			if(num == 1) response = "As much as you need nutrition, being overweight or obese isn't going to help you.";
			else if(num == 2) response = "Your weight can not only deteriorate your body, but it can also deteriorate your self-esteem and self-trust.";
			else if(num == 3) response = "Lose weight! I cannot emphasize that further. Aim for getting a normal BMI.";
			break;
		case BN:
			if(num == 1) response = "There's really no point in adding more weight to yourself.";
			else if(num == 2) response = "Change your goals to something more rational. Gaining weight isn't one of them.";
			else if(num == 3) response = "\"LOSE WEIGHT.\" There, I said it.";
			break;
		default: return "I could not give you an advice based on the information you provided. Try fixing your information.";
		}
		return response;
	}
}
