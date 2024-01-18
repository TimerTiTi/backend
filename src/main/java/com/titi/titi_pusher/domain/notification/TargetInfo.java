package com.titi.titi_pusher.domain.notification;

public sealed interface TargetInfo {

	TargetType getType();

	String getTargetValue();

	enum TargetType {
		/**
		 * Target value would be encrypted and stored as the recipient's email address in the database.
		 */
		EMAIL
	}

	record EmailTargetInfo(
		String email
	) implements TargetInfo {

		@Override
		public TargetType getType() {
			return TargetType.EMAIL;
		}

		@Override
		public String getTargetValue() {
			return this.email;
		}

	}

}
